package sakura.arachnida.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sakura.arachnida.models.Board;
import sakura.arachnida.models.Boardseat;
import sakura.arachnida.service.BoardseatService;
import sakura.arachnida.service.BoardService; // Предполагается, что существует сервис для Board
import sakura.arachnida.service.ModelService; // Предполагается, что существует сервис для Model
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/boardseat")
public class BoardseatController {

    private final BoardseatService boardseatService;
    private final BoardService boardService; // Сервис для работы с Board
    private final ModelService modelService; // Сервис для работы с Model

    public BoardseatController(BoardseatService boardseatService, BoardService boardService, ModelService modelService) {
        this.boardseatService = boardseatService;
        this.boardService = boardService;
        this.modelService = modelService;
    }

    @GetMapping
    public String getAllBoardSeats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filterSeatType,
            @RequestParam(required = false) String filterRowNumber,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false) Boolean filterBoard,
            @RequestParam(defaultValue = "seattype") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Boardseat> boardseatPage = boardseatService.findAllWithPaginationAndFilters(
                pageable, filterSeatType, filterRowNumber, "", isDeleted, sortField, sortDirection);

        model.addAttribute("boardseats", boardseatPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardseatPage.getTotalPages());

        model.addAttribute("boards", boardService.findAll());

        return "boardseatPage";
    }

    @GetMapping("/create")
    public String getCreateBoardseat(Model model) {
        model.addAttribute("seatTypes", List.of("standard", "business")); // Хардкод типов мест
        model.addAttribute("boards", boardService.findAll()); // Подгрузка всех бордов
        model.addAttribute("models", modelService.findAll()); // Подгрузка всех моделей
        return "createBoardseat";
    }

    @PostMapping("/create")
    public String postCreateBoardseat(
            @RequestParam(name = "seattype") String seattype,
            @RequestParam(name = "rownumber") Integer rownumber,
            @RequestParam(name = "seatnumber") Integer seatnumber,
            @RequestParam(name = "board") Integer boardId
    ) {

        Boardseat newBoardseat = new Boardseat();
        newBoardseat.setSeattype(seattype);
        newBoardseat.setRownumber(rownumber);
        newBoardseat.setSeatnumber(seatnumber);
        newBoardseat.setBoard(boardService.findByOkId(boardId));
        boardseatService.create(newBoardseat);

        return "redirect:/boardseat";
    }

    @GetMapping("/edit/{id}")
    public String getEditBoardseat(@PathVariable Integer id, Model model) {
        var boardseat = boardseatService.findById(id);
        model.addAttribute("boardseat", boardseat);
        model.addAttribute("seatTypes", List.of("standard", "business")); // Хардкод типов мест
        model.addAttribute("boards", boardService.findAll()); // Подгрузка всех бордов
        model.addAttribute("models", modelService.findAll()); // Подгрузка всех моделей
        return "editBoardseat";
    }

    @PostMapping("/edit/{id}")
    public String postEditBoardseat(
            @PathVariable Integer id,
            @RequestParam(name = "seattype") String seattype,
            @RequestParam(name = "rownumber") Integer rownumber,
            @RequestParam(name = "seatnumber") Integer seatnumber,
            @RequestParam(name = "board") Integer boardId
    ) {
        var boardseat = new Boardseat();
        boardseat.setId(id);
        boardseat.setSeattype(seattype);
        boardseat.setRownumber(rownumber);
        boardseat.setSeatnumber(seatnumber);
        boardseat.setBoard(boardService.findByOkId(boardId));
        boardseatService.update(boardseat);
        return "redirect:/boardseat";
    }

    @PostMapping("/remove")
    public String logicDeleteBoardseats(@RequestParam List<Integer> ids) {
        boardseatService.logicDelete(ids);
        return "redirect:/boardseat";
    }

    @PostMapping("/delete")
    public String deleteBoardseats(@RequestParam List<Integer> ids) {
        boardseatService.delete(ids);
        return "redirect:/boardseat";
    }




    @GetMapping("/all")
    public ResponseEntity<List<Boardseat>> getAllBoardSeatsApi() {
        List<Boardseat> boardseats = boardseatService.findAll();
        return ResponseEntity.ok(boardseats);
    }

    // API endpoint to get a board seat by ID
    @GetMapping("/{id}")
    public ResponseEntity<Boardseat> getBoardSeatByIdApi(@PathVariable Integer id) {
        Boardseat boardseat = boardseatService.findById(id);
        return boardseat != null
                ? ResponseEntity.ok(boardseat)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // API endpoint to create a new board seat
    @PostMapping
    public ResponseEntity<Boardseat> createBoardSeatApi(@RequestBody Boardseat newBoardseat) {
        Boardseat createdBoardseat = boardseatService.create(newBoardseat);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoardseat);
    }

    // API endpoint to update an existing board seat
    @PutMapping("/{id}")
    public ResponseEntity<Boardseat> updateBoardSeatApi(@PathVariable Integer id, @RequestBody Boardseat updatedBoardseat) {
        updatedBoardseat.setId(id);
        Boardseat boardseat = boardseatService.update(updatedBoardseat);
        return boardseat != null
                ? ResponseEntity.ok(boardseat)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // API endpoint to delete a board seat
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardSeatApi(@PathVariable Integer id) {
        boolean deleted = boardseatService.deleteById(id); // Assuming you have a method in your service
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}