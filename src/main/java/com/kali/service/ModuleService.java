package com.kali.service;

import com.kali.entity.Module;
import com.kali.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public Optional<Module> getModuleById(Long id) {
        return moduleRepository.findById(id);
    }

    public Module createModule(Module module) {
        return moduleRepository.save(module);
    }

    public Module updateModule(Long id, Module moduleDetails) {
        return moduleRepository.findById(id)
                .map(module -> {
                    module.setTitle(moduleDetails.getTitle());
                    module.setCourse(moduleDetails.getCourse());
                    module.setOrderIndex(moduleDetails.getOrderIndex());
                    return moduleRepository.save(module);
                })
                .orElseThrow(() -> new RuntimeException("Module not found"));
    }

    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }
}
