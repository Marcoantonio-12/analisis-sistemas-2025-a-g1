package com.proyecto.GestionContactos.Service;

import com.proyecto.GestionContactos.Entity.Contacto;
import com.proyecto.GestionContactos.IRepository.IContactoRepository;
import com.proyecto.GestionContactos.IService.IContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoService implements IContactoService {
    @Autowired
    private IContactoRepository repository;

    @Override
    public Contacto save(Contacto contacto) {
        return repository.save(contacto);
    }

    @Override
    public Contacto update(Contacto contacto, Integer id) { // <--- Cambiado a retornar Contacto
        Optional<Contacto> up = repository.findById(id);
        if(up.isPresent()){
            Contacto contactoActual = up.get();
            contactoActual.setNombres(contacto.getNombres());
            contactoActual.setApellidos(contacto.getApellidos());
            contactoActual.setTelefono(contacto.getTelefono());

            // Si el usuarioId se puede actualizar, también debe ser manejado aquí
            // Esto es crucial para la relación ManyToOne.
            // Si el usuario_id no se espera cambiar en la actualización, puedes omitir la siguiente línea
            // o asegurar que el usuarioId del contacto entrante sea el mismo que el existente.
            contactoActual.setUsuarioId(contacto.getUsuarioId()); 

            return repository.save(contactoActual); // <--- Retorna el contacto actualizado
        }else{
            System.out.println("No existe registro de contacto para actualizar con ID: " + id);
            return null; // O considera lanzar una excepción específica (ej. ResourceNotFoundException)
        }
    }

    @Override
    public List<Contacto> all() {
        return repository.findAll();
    }

    @Override
    public Optional<Contacto> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Contacto> findByUsuario(String nombreUsuario) {
        return repository.findByUsuarioId_NombreUsuario(nombreUsuario);
    }
}