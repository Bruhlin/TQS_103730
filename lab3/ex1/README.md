## a) Identify a couple of examples that use AssertJ expressive methods chaining.

A -> assertThat(found).isNotNull().extracting(Employee::getName).isEqualTo(persistedAlex.getName());
A -> assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
D -> assertThat(found).extracting(Employee::getName).containsOnly("bob");
E -> assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");


## b) Take note of transitive annotations included in @DataJpaTest.

@BootstrapWith(SpringBootTestContextBootstrapper.class) -> Boots up a Spring application context suitable for testing;

@ExtendWith(SpringExtension.class) -> Integrates JUnit 5 with the Spring framework;

@Transactional -> Ensures that each test method runs in a transaction and is rolled back at the end;

@AutoConfigureTestDatabase -> Configures an embedded database if no database is explicitly configured.

@AutoConfigureDataJpa -> Configures JPA repositories automatically.


## c) Identify an example in which you mock the behavior of the repository (and avoid involving a database). 

In B: 

@BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }




## d) What is the difference between standard @Mock and @MockBean?

@Mock:

- Provided by Mockito;
- Works purely in Mockito's context, outside of Spring;
- Creates a mock instance of a class or interface;
- Does not inject into the Spring application context;
- Typically used in unit tests where Spring context is not required;

@MockBean:

- Provided by SpringBoot;
- Works inside the Spring ApplicationContext;
- Replaces an existing bean in the Spring container with a mock;
- Automatically injects the mock into Spring beans that depend on it;
- Used in integration or Spring Boot tests where beans are managed by Spring;


## e) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

It is typically used to configure integration tests. It allows a separate configuration that is optimized for testing, ensuring that tests are run in an environment isolated from production or development settings. In this case, the file is configuring a MySQL test database that is running via Docker.

This file will be used when it is explicitly loaded in a test class using @TestPropertySource:

SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = EmployeeMngrApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
// adapt AutoConfigureTestDatabase with TestPropertySource to use a real database
// @TestPropertySource(locations = "application-integrationtest.properties")
class D_EmployeeRestControllerIT {...}

## f) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences? 

C:

- Uses @MockBean to mock the EmployeeService dependency;
- Loads only the controller (@WebMvcTest(EmployeeRestController.class)) instead of the full application.
- No real database is used (only service logic is tested);
- Uses MockMvc to simulate HTTP requests.

D:

- Uses real service and repository (no @MockBean);
- Loads the full Spring Boot context (@SpringBootTest);
- Uses an in-memory database (@AutoConfigureTestDatabase);
- Uses MockMvc to send actual HTTP requests.

E:

- Uses real service and repository (no mocks);
- Loads the full Spring Boot app (@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT));
- Uses either an in-memory or real database (@AutoConfigureTestDatabase or @TestPropertySource);
- Uses TestRestTemplate to send real HTTP requests (network stack is used).





                  
