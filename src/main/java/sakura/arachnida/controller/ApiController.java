package sakura.arachnida.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sakura.arachnida.models.*;
import sakura.arachnida.service.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardseatService boardseatService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightseatService flightseatService;

    @Autowired
    private ModelService modelService;

    // User Endpoints
    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(201).body(savedUser);
    }


    // Ticket Endpoints
    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> listTickets() {
        List<Ticket> tickets = ticketService.findAll();
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/tickets/create")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.create(ticket);
        return ResponseEntity.status(201).body(createdTicket);
    }

    @PostMapping("/tickets/delete/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Manufacturer Endpoints
    @GetMapping("/manufacturers")
    public ResponseEntity<List<Manufacturer>> listManufacturers() {
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        return ResponseEntity.ok(manufacturers);
    }

    @PostMapping("/manufacturers/create")
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody Manufacturer manufacturer) {
        Manufacturer createdManufacturer = manufacturerService.create(manufacturer);
        return ResponseEntity.status(201).body(createdManufacturer);
    }

    @PostMapping("/manufacturers/delete/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Integer id) {
        manufacturerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Board Endpoints
    @GetMapping("/boards")
    public ResponseEntity<List<Board>> listBoards() {
        List<Board> boards = boardService.findAll();
        return ResponseEntity.ok(boards);
    }

    @PostMapping("/boards/create")
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        Board createdBoard = boardService.create(board);
        return ResponseEntity.status(201).body(createdBoard);
    }

    @PostMapping("/boards/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Integer id) {
        boardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Boardseat Endpoints
    @GetMapping("/boardseats")
    public ResponseEntity<List<Boardseat>> listBoardseats() {
        List<Boardseat> boardseats = boardseatService.findAll();
        return ResponseEntity.ok(boardseats);
    }

    @PostMapping("/boardseats/create")
    public ResponseEntity<Boardseat> createBoardseat(@RequestBody Boardseat boardseat) {
        Boardseat createdBoardseat = boardseatService.create(boardseat);
        return ResponseEntity.status(201).body(createdBoardseat);
    }

    @PostMapping("/boardseats/delete/{id}")
    public ResponseEntity<Void> deleteBoardseat(@PathVariable Integer id) {
        boardseatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Client Endpoints
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> listClients() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/clients/create")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client createdClient = clientService.create(client);
        return ResponseEntity.status(201).body(createdClient);
    }

    @PostMapping("/clients/delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Employee Endpoints
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> listEmployees() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/employees/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.create(employee);
        return ResponseEntity.status(201).body(createdEmployee);
    }

    @PostMapping("/employees/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Flight Endpoints
    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> listFlights() {
        List<Flight> flights = flightService.findAll();
        return ResponseEntity.ok(flights);
    }

    @PostMapping("/flights/create")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight createdFlight = flightService.create(flight);
        return ResponseEntity.status(201).body(createdFlight);
    }

    @PostMapping("/flights/delete/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Integer id) {
        flightService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Flightseat Endpoints
    @GetMapping("/flightseats")
    public ResponseEntity<List<Flightseat>> listFlightseats() {
        List<Flightseat> flightseats = flightseatService.findAll();
        return ResponseEntity.ok(flightseats);
    }

    @PostMapping("/flightseats/create")
    public ResponseEntity<Flightseat> createFlightseat(@RequestBody Flightseat flightseat) {
        Flightseat createdFlightseat = flightseatService.create(flightseat);
        return ResponseEntity.status(201).body(createdFlightseat);
    }

    @PostMapping("/flightseats/delete/{id}")
    public ResponseEntity<Void> deleteFlightseat(@PathVariable Integer id) {
        flightseatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Model Endpoints
    @GetMapping("/models")
    public ResponseEntity<List<Model>> listModels() {
        List<Model> models = modelService.findAll();
        return ResponseEntity.ok(models);
    }

    @PostMapping("/models/create")
    public ResponseEntity<Model> createModel(@RequestBody Model model) {
        Model createdModel = modelService.create(model);
        return ResponseEntity.status(201).body(createdModel);
    }

    @PostMapping("/models/delete/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Integer id) {
        modelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}