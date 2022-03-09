package br.com.eventos.controllers;

import br.com.eventos.models.Evento;
import br.com.eventos.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EventoController {

    @Autowired
    EventoRepository  eventoRepository;

    @GetMapping("")
    public String index(){
        return "evento/index";
    }

    @GetMapping("/eventos")
    public ModelAndView verTodosEventos(){
//         lISTA DE EVENTOS
        List<Evento> listaEventos = new ArrayList<>();
        listaEventos = eventoRepository.findAll();
        ModelAndView mv = new ModelAndView("evento/eventos");
        mv.addObject("listaEventos", listaEventos );
        return mv;
    }

    @GetMapping("/eventos/novo")
    public String cadastar(){
        return "evento/cadastro";
    }

    @PostMapping("/eventos/cadastrar")
    public String cadastrarEvento(Evento evento){

        eventoRepository.save(evento);

        return "redirect:/eventos";
    }

    @GetMapping("/eventos/{id}")
    public ModelAndView detalhes(@PathVariable Long id){
        Optional<Evento> evento = eventoRepository.findById(id);

        if (evento.isPresent()) {
            ModelAndView mv = new ModelAndView("evento/detalhes");
            mv.addObject("evento", evento.get() );
            return mv;
        } else {
            return new ModelAndView("redirect:/eventos");
        }
    }

    @GetMapping("/eventos/deletar/{id}")
    public String deletar(@PathVariable Long id){

        eventoRepository.deleteById(id);
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/{id}/editar")
    public ModelAndView paginaEditar(@PathVariable Long id){
        Optional<Evento> evento = eventoRepository.findById(id);

        if (evento.isPresent()) {
            ModelAndView mv = new ModelAndView("evento/editar");
            mv.addObject("evento", evento.get() );
            return mv;
        } else {
            return new ModelAndView("redirect:/eventos");
        }

    }

}
