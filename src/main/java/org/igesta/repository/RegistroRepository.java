package org.igesta.repository;

import org.igesta.model.Registro;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface RegistroRepository extends MongoRepository<Registro, String> {

    List<Registro> findByDataBetweenAndUnidade(Date dataInicio, Date dataFim, String unidade);

    @Aggregation(pipeline = {
            "{ $unwind: '$condenas' }",
            "{ $match: { 'condenas.tipo': 'Total', 'unidade': ?0 } }",
            "{ $group: { _id: '$condenas.nome', totalQuantidade: { $sum: '$condenas.quantidade' } } }",
            "{ $sort: { totalQuantidade: -1 } }",
            "{ $limit: 3 }",
            "{ $project: { _id: 0, nome: '$_id', totalQuantidade: 1 } }"
    })
    List<Map<String, Object>> buscarTop3CondenasTotalPorUnidade(String unidade);

    @Aggregation(pipeline = {
            "{ $unwind: '$condenas' }",
            "{ $match: { 'condenas.tipo': 'Parcial', 'unidade': ?0 } }",
            "{ $group: { _id: '$condenas.nome', totalQuantidade: { $sum: '$condenas.quantidade' } } }",
            "{ $sort: { totalQuantidade: -1 } }",
            "{ $limit: 3 }",
            "{ $project: { _id: 0, nome: '$_id', totalQuantidade: 1 } }"
    })
    List<Map<String, Object>> buscarTop3CondenasParcialPorUnidade(String unidade);

    @Aggregation(pipeline = {
        "{ $unwind: '$condenas' }",
                "{ $match: { 'condenas.tipo': 'Parcial', 'unidade': ?0 } }",
                "{ $group: { _id: null, totalQuantidade: { $sum: '$condenas.quantidade' } } }",
                "{ $project: { _id: 0, totalQuantidade: 1 } }"
    })
    List<Map<String, Object>> somarCondenasParcialPorUnidade(String unidade);

    @Aggregation(pipeline = {
            "{ $unwind: '$condenas' }",
            "{ $match: { 'condenas.tipo': 'Total', 'unidade': ?0 } }",
            "{ $group: { _id: null, totalQuantidade: { $sum: '$condenas.quantidade' } } }",
            "{ $project: { _id: 0, totalQuantidade: 1 } }"
    })
    List<Map<String, Object>> somarCondenasTotalPorUnidade(String unidade);


    @Aggregation(pipeline = {
            "{ $unwind: '$condenas' }",
            "{ $match: { 'unidade': ?0 } }",
            "{ $addFields: { mes: { $month: '$data' } } }",
            "{ $match: { mes: ?1 } }",
            "{ $group: { _id: null, totalQuantidade: { $sum: '$condenas.quantidade' } } }",
            "{ $project: { _id: 0, totalQuantidade: 1 } }"
    })
    List<Map<String, Object>> somarTodasCondenasPorMes(String unidade, int mes);

    @Aggregation(pipeline = {
            "{ $match: { unidade: ?0 } }",
            "{ $unwind: '$condenas' }",
            "{ $group: { _id: null, totalQuantidade: { $sum: '$condenas.quantidade' } } }"
    })
    List<Map<String, Object>> contarTodasCondenasPorUnidade(String unidade);
}