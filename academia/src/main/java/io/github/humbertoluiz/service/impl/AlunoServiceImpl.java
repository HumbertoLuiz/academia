package io.github.humbertoluiz.service.impl;

import io.github.humbertoluiz.entity.Aluno;
import io.github.humbertoluiz.entity.AvaliacaoFisica;
import io.github.humbertoluiz.entity.form.AlunoForm;
import io.github.humbertoluiz.entity.form.AlunoUpdateForm;
import io.github.humbertoluiz.entity.form.MatriculaForm;
import io.github.humbertoluiz.infra.utils.JavaTimeUtils;
import io.github.humbertoluiz.repository.AlunoRepository;
import io.github.humbertoluiz.service.IAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlunoServiceImpl implements IAlunoService {

  @Autowired
  private AlunoRepository repository;

  @Override
  public Aluno create(AlunoForm form) {
    Aluno aluno = new Aluno();
    aluno.setNome(form.getNome());
    aluno.setCpf(form.getCpf());
    aluno.setBairro(form.getBairro());
    aluno.setDataDeNascimento(form.getDataDeNascimento());

    return repository.save(aluno);
  }

  @Override
  public Aluno get(Long id) {
	  return repository.findById(id).get();
  }

  @Override
  public List<Aluno> getAll(String dataDeNascimento) {

    if(dataDeNascimento == null) {
      return repository.findAll();
    } else {
      LocalDate localDate = LocalDate.parse(dataDeNascimento, JavaTimeUtils.LOCAL_DATE_FORMATTER);
      return repository.findByDataDeNascimento(localDate);
    }

  }

	@Override
	public void delete(Long id) {
		// Deletar Cliente por ID.
		repository
		.findById(id)
		.map( aluno -> {
			repository.delete(aluno);
			return Void.TYPE;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "não encontrado"));
	}

	@Override
	public void update(Long id, AlunoForm form) {
		repository.findById(id)
		.map(alunoExistente -> {		
		return repository.save(alunoExistente);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "não encontrado"));
	}

  @Override
  public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(Long id) {

    Aluno aluno = repository.findById(id).get();

    return aluno.getAvaliacoes();

  }

}
