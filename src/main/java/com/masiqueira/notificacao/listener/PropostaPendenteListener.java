package com.masiqueira.notificacao.listener;

import com.masiqueira.notificacao.constante.MensagemConstante;
import com.masiqueira.notificacao.domain.Proposta;
import com.masiqueira.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaPendenteListener {

    private NotificacaoSnsService notificacaoSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaPendente(Proposta proposta) {
        String mensagem = String.format(MensagemConstante.PROPOSTA_EM_ANALISE, proposta.getUsuario().getNome());
        notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensagem);
    }
}
