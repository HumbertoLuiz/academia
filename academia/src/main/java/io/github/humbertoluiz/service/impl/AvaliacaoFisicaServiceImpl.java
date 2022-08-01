package io.github.humbertoluiz.service.impl;

import io.github.humbertoluiz.entity.Aluno;
import io.github.humbertoluiz.entity.AvaliacaoFisica;
import io.github.humbertoluiz.entity.form.AvaliacaoFisicaForm;
import io.github.humbertoluiz.entity.form.AvaliacaoFisicaUpdateForm;
import io.github.humbertoluiz.entity.form.MatriculaForm;
import io.github.humbertoluiz.repository.AlunoRepository;
import io.github.humbertoluiz.repository.AvaliacaoFisicaRepository;
import io.github.humbertoluiz.service.IAvaliacaoFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AvaliacaoFisicaServiceImpl implements IAvaliacaoFisicaService {

  @Autowired
  private AvaliacaoFisicaRepository avaliacaoFisicaRepository;

  @Autowired
  private AlunoRepository alunoRepository;

  @Override
  public AvaliacaoFisica create(AvaliacaoFisicaForm form) {
    AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();
    Aluno aluno = alunoRepository.findById(form.getAlunoId()).get();

    avaliacaoFisica.setAluno(aluno);
    avaliacaoFisica.setPeso(form.getPeso());
    avaliacaoFisica.setAltura(form.getAltura());

    return avaliacaoFisicaRepository.save(avaliacaoFisica);
  }

  @Override
  public AvaliacaoFisica get(Long id) {
    return null;
  }

  @Override
  public List<AvaliacaoFisica> getAll() {

    return avaliacaoFisicaRepository.findAll();
  }

	@Override
	public void delete(Long id) {
		// Deletar Cliente por ID.
		avaliacaoFisicaRepository
		.findById(id)
		.map( avaliacao -> {
			avaliacaoFisicaRepository.delete(avaliacao);
			return Void.TYPE;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "não encontrado"));
	}

	@Override
	public void update(Long id, AvaliacaoFisicaForm form) {
		avaliacaoFisicaRepository.findById(id)
		.map(avaliacao -> {
		form.setAlunoId(id);
		return avaliacaoFisicaRepository.save(avaliacao);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "não encontrado"));
	}
}
