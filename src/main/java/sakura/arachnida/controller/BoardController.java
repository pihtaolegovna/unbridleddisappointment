package sakura.arachnida.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import sakura.arachnida.models.Board;
import sakura.arachnida.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import sakura.arachnida.models.Manufacturer;
import sakura.arachnida.service.ManufacturerService;
import sakura.arachnida.service.ModelService;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final ManufacturerService manufacturerService;
    private final ModelService modelService;

    public BoardController(BoardService boardService, ManufacturerService manufacturerService, ModelService modelService) {
        this.boardService = boardService;
        this.manufacturerService = manufacturerService;
        this.modelService = modelService;
    }

    @GetMapping
    public String getAllBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filterBoardNumber,
            @RequestParam(required = false) String filterYear,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(defaultValue = "boardnumber") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.findAllWithPaginationAndFilters(
                pageable, filterBoardNumber, filterYear, isDeleted, sortField, sortDirection);

        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardPage.getTotalPages());

        List<Manufacturer> manufacturers = manufacturerService.findAll(); // Fetch manufacturers
        model.addAttribute("manufacturers", manufacturers); // Add to model

        List<sakura.arachnida.models.Model> models = modelService.findAll(); // Fetch manufacturers
        model.addAttribute("models", models); // Add to model

        return "boardPage";
    }

    @GetMapping("/create")
    public String getCreateBoard(Model model) {
        return "createBoard";
    }

    @PostMapping("/create")
    public String postCreateBoard(
            @RequestParam(name = "boardnumber") String boardnumber,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "seatsamount") Integer seatsamount,
            @RequestParam(name = "model", required = false) Integer model
    ) {
        Board newBoard = new Board();
        newBoard.setBoardnumber(boardnumber);
        newBoard.setYear(year);
        newBoard.setSeatsamount(seatsamount);
        newBoard.setModel(modelService.findById(model));
        boardService.create(newBoard);

        return "redirect:/board";
    }

    @GetMapping("/edit/{id}")
    public String getEditBoard(@PathVariable UUID id, Model model) {
        var board = boardService.findById(id);
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        model.addAttribute("board", board);
        model.addAttribute("manufacturers", manufacturers);
        return "redirect:/board";
    }

    @PostMapping("/edit/{id}")
    public String postEditBoard(
            @PathVariable Integer id,
            @RequestParam(name = "boardnumber") String boardnumber,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "seatsamount") Integer seatsamount,
            @RequestParam(name = "model", required = false) Integer model
    ) {
        var board = new Board();
        board.setId(id);
        board.setBoardnumber(boardnumber);
        board.setYear(year);
        board.setModel(modelService.findById(model));
        board.setSeatsamount(seatsamount);
        boardService.update(board);
        return "redirect:/board";
    }

    @PostMapping("/remove")
    public String logicDeleteBoards(@RequestParam List<UUID> ids) {
        boardService.logicDelete(ids);
        return "redirect:/board";
    }

    @PostMapping("/delete")
    public String deleteBoards(@RequestParam List<UUID> ids) {
        boardService.delete(ids);
        return "redirect:/board";
    }
}