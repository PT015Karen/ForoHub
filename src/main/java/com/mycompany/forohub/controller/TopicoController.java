/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.forohub.controller;

import com.mycompany.forohub.model.Topico;
import com.mycompany.forohub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<Topico> crearTopico(@RequestBody @Validated Topico topico) {
        if (topicoRepository.existsByTituloAndMensaje(topico.getTitulo(), topico.getMensaje())) {
            return ResponseEntity.badRequest().build();
        }
        Topico nuevoTopico = topicoRepository.save(topico);
        return ResponseEntity.ok(nuevoTopico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @RequestBody @Validated Topico topicoActualizado) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            Topico topicoExistente = optionalTopico.get();
            topicoExistente.setTitulo(topicoActualizado.getTitulo());
            topicoExistente.setMensaje(topicoActualizado.getMensaje());
            topicoExistente.setStatus(topicoActualizado.getStatus());
            topicoExistente.setCurso(topicoActualizado.getCurso());
            topicoExistente.setAutor(topicoActualizado.getAutor());
            topicoRepository.save(topicoExistente);
            return ResponseEntity.ok(topicoExistente);
        }
        return ResponseEntity.notFound().build();
    }
}



