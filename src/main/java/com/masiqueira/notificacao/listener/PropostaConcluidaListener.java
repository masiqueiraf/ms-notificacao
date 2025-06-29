package com.masiqueira.notificacao.listener;

import com.masiqueira.notificacao.constante.MensagemConstante;
import com.masiqueira.notificacao.domain.Proposta;
import com.masiqueira.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PropostaConcluidaListener {

    private NotificacaoSnsService notificacaoSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta) {
        String nome = proposta.getUsuario().getNome();
        String mensagem = proposta.getAprovada()
                ? String.format(MensagemConstante.PROPOSTA_APROVADA, nome)
                : (Objects.nonNull(proposta.getObservacao()))
                ? String.format(MensagemConstante.PROPOSTA_NEGADA_POR_STRATEGY, nome, proposta.getObservacao())
                : String.format(MensagemConstante.PROPOSTA_NEGADA, nome);

        notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensagem);
    }
}
