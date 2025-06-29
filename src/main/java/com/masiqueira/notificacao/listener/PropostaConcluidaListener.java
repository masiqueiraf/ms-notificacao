package com.masiqueira.notificacao.listener;

import com.masiqueira.notificacao.constante.MensagemConstante;
import com.masiqueira.notificacao.domain.Proposta;
import com.masiqueira.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    private NotificacaoSnsService notificacaoSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta) {
        String mensagem = String.format(MensagemConstante.PROPOSTA_CONCLUIDA, proposta.getUsuario().getNome());
        notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensagem);
    }
}
