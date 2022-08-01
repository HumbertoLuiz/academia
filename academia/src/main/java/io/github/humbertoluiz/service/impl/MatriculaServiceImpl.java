package io.github.humbertoluiz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.github.humbertoluiz.entity.Aluno;
import io.github.humbertoluiz.entity.Matricula;
import io.github.humbertoluiz.entity.form.MatriculaForm;
import io.github.humbertoluiz.repository.AlunoRepository;
import io.github.humbertoluiz.repository.MatriculaRepository;
import io.github.humbertoluiz.service.IMatriculaService;

@Service
public class MatriculaServiceImpl implements IMatriculaService {

	@Autowired
	private MatriculaRepository matriculaRepository;

	@Autowired
	private AlunoRepository alunoRepository;

	@Override
	public Matricula create(MatriculaForm form) {
		Matricula matricula = new Matricula();
		Aluno aluno = alunoRepository.findById(form.getAlunoId()).get();

		matricula.setAluno(aluno);

		return matriculaRepository.save(matricula);
	}

	@Override
	public Matricula get(Long id) {
		return matriculaRepository.findById(id).get();
	}

	@Override
	public List<Matricula> getAll(String bairro) {

		if (bairro == null) {
			return matriculaRepository.findAll();
		} else {
			return matriculaRepository.findAlunosMatriculadosBairro(bairro);
		}

	}

	@Override
	public void delete(Long id) {
		// Deletar Cliente por ID.
		matriculaRepository
		.findById(id)
		.map( matricula -> {
			matriculaRepository.delete(matricula);
			return Void.TYPE;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "não encontrado"));
	}

	@Override
	public void update(Long id, MatriculaForm form) {
		matriculaRepository.findById(id)
		.map(matricula -> {
		form.setAlunoId(id);
		return matriculaRepository.save(matricula);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "não encontrado"));
	}
	
}
