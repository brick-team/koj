package com.github.brick.action.flow.parse.swagger;

import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.entity.api.ParamEntity;
import io.swagger.models.*;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.properties.Property;
import io.swagger.parser.SwaggerParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwaggerFIleParseImpl implements SwaggerFIleParse {
    private List<ParamEntity> extracted(Operation operation, Map<String, Model> modelMap) {
        List<ParamEntity> res = new ArrayList<>();
        if (operation != null) {

            List<Parameter> parameters = operation.getParameters();
            for (Parameter parameter : parameters) {
                String in = parameter.getIn();
                ParamIn paramIn = ParamIn.valueOf(in);
                ParamEntity paramEntity = new ParamEntity();
                paramEntity.setIn(paramIn);

                // todo: 讨论是否需要做出不同的数据处理
                if (paramIn == ParamIn.body) {
                    BodyParameter bodyParameter = (BodyParameter) parameter;
                    Model schema = bodyParameter.getSchema();
                    if (schema instanceof RefModel) {
                        String simpleRef = ((RefModel) schema).getSimpleRef();
                        Model model = modelMap.get(simpleRef);
                        if (model instanceof ModelImpl) {

                            Map<String, Property> properties = model.getProperties();
                            List<String> required = ((ModelImpl) model).getRequired();
                            properties.forEach((k, v) -> {
                                String type = v.getType();
                                String name = k;
                                boolean require = false;
                                if (required != null) {
                                    require = required.contains(name);
                                }
                                paramEntity.setName(name);
                                paramEntity.setRequire(require);
                            });
                        }
                    }
                    System.out.println();
                } else if (paramIn == ParamIn.path) {
                    PathParameter pathParameter = (PathParameter) parameter;
                    String name = pathParameter.getName();
                    boolean required = pathParameter.getRequired();
                    paramEntity.setName(name);
                    paramEntity.setRequire(required);
                    String type1 = pathParameter.getType();
                } else if (paramIn == ParamIn.formData) {
                    FormParameter formParameter = (FormParameter) parameter;
                    String type = formParameter.getType();

                    String name = formParameter.getName();
                    boolean required = formParameter.getRequired();
                    paramEntity.setName(name);
                    paramEntity.setRequire(required);
                }


                res.add(paramEntity);
            }
        }

        return res;
    }

    @Override
    public List<ApiEntity> parse(String file) {
        SwaggerParser sw = new SwaggerParser();
        Swagger read = sw.read("swagge_example.json");
        Map<String, Path> paths = read.getPaths();

        Map<String, Model> definitions = read.getDefinitions();

        List<ApiEntity> res = new ArrayList<>();

        paths.forEach(
                (k, v) -> {
                    ApiEntity swaggerApiEntity = genSwaggerApiEntity(k, v, definitions);
                    res.add(swaggerApiEntity);

                }
        );


        return res;
    }

    private ApiEntity genSwaggerApiEntity(String k, Path v, Map<String, Model> modelMap) {

        ApiEntity swaggerApiEntity = new ApiEntity();
        swaggerApiEntity.setUrl(k);

        Operation get = v.getGet();
        Operation post = v.getPost();
        Operation put = v.getPut();
        Operation delete = v.getDelete();

        String[] httpMethodSupport = {
                get != null ? "get" : null,
                post != null ? "post" : null,
                put != null ? "put" : null,
                delete != null ? "delete" : null
        };
        swaggerApiEntity.setMethod(httpMethodSupport);


        List<ParamEntity> getParamList = extracted(get, modelMap);
        List<ParamEntity> postParamList = extracted(post, modelMap);
        List<ParamEntity> putParamList = extracted(put, modelMap);
        List<ParamEntity> deleteParamList = extracted(delete, modelMap);

        Map<String, List<ParamEntity>> map = new HashMap<>(4);
        map.put("get", getParamList);
        map.put("post", postParamList);
        map.put("put", putParamList);
        map.put("delete", deleteParamList);
        swaggerApiEntity.setParams(map);
        return swaggerApiEntity;
    }

}
