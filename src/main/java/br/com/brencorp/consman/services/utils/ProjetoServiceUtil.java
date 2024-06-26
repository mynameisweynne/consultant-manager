package br.com.brencorp.consman.services.utils;

import br.com.brencorp.consman.dto.ProjetoDTO;
import br.com.brencorp.consman.entities.Projeto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjetoServiceUtil {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static Projeto insert(ProjetoDTO projetoDTO) {
        return MODEL_MAPPER.map(projetoDTO, Projeto.class);
    }

    public static void update(Projeto projeto, ProjetoDTO projetoDTO) {
        projeto.setNome(projetoDTO.getNome());
    }
}
