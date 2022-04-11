/*
 *    Copyright [2022] [brick-team]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.brick.action.flow.parse.swagger;

import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.google.gson.Gson;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.junit.Test;

import java.util.List;

public class SwaggerParseImplTest {
    SwaggerParse swaggerParse = new SwaggerParseImpl();
    Gson gson = new Gson();

    @org.junit.Test
    public void parse() {
        List<ApiEntity> parse = swaggerParse.parseFile("swagge_example.json");
        for (ApiEntity apiEntity : parse) {
            List<ApiParamEntity> params = apiEntity.getParams();
            System.out.println(gson.toJson(apiEntity));
        }


    }

    @Test
    public void swData() {
        SwaggerParser sw = new SwaggerParser();
        Swagger read = sw.parse("{\n" +
                "  \"swagger\": \"2.0\",\n" +
                "  \"info\": {\n" +
                "    \"description\": \"This is a sample server Petstore server.  You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).  For this sample, you can use the api key `special-key` to test the authorization filters.\",\n" +
                "    \"version\": \"1.0.6\",\n" +
                "    \"title\": \"Swagger Petstore\",\n" +
                "    \"termsOfService\": \"http://swagger.io/terms/\",\n" +
                "    \"contact\": {\n" +
                "      \"email\": \"apiteam@swagger.io\"\n" +
                "    },\n" +
                "    \"license\": {\n" +
                "      \"name\": \"Apache 2.0\",\n" +
                "      \"url\": \"http://www.apache.org/licenses/LICENSE-2.0.html\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"host\": \"petstore.swagger.io\",\n" +
                "  \"basePath\": \"/v2\",\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"name\": \"pet\",\n" +
                "      \"description\": \"Everything about your Pets\",\n" +
                "      \"externalDocs\": {\n" +
                "        \"description\": \"Find out more\",\n" +
                "        \"url\": \"http://swagger.io\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"store\",\n" +
                "      \"description\": \"Access to Petstore orders\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"user\",\n" +
                "      \"description\": \"Operations about user\",\n" +
                "      \"externalDocs\": {\n" +
                "        \"description\": \"Find out more about our store\",\n" +
                "        \"url\": \"http://swagger.io\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"schemes\": [\n" +
                "    \"https\",\n" +
                "    \"http\"\n" +
                "  ],\n" +
                "  \"paths\": {\n" +
                "    \"/pet/{petId}/uploadImage\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"uploads an image\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"uploadFile\",\n" +
                "        \"consumes\": [\n" +
                "          \"multipart/form-data\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"petId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"ID of pet to update\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"format\": \"int64\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"additionalMetadata\",\n" +
                "            \"in\": \"formData\",\n" +
                "            \"description\": \"Additional data to pass to server\",\n" +
                "            \"required\": false,\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"file\",\n" +
                "            \"in\": \"formData\",\n" +
                "            \"description\": \"file to upload\",\n" +
                "            \"required\": false,\n" +
                "            \"type\": \"file\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/ApiResponse\"\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"/pet\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Add a new pet to the store\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"addPet\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"Pet object that needs to be added to the store\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Pet\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"405\": {\n" +
                "            \"description\": \"Invalid input\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"put\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Update an existing pet\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"updatePet\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"Pet object that needs to be added to the store\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Pet\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid ID supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"Pet not found\"\n" +
                "          },\n" +
                "          \"405\": {\n" +
                "            \"description\": \"Validation exception\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"/pet/findByStatus\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Finds Pets by status\",\n" +
                "        \"description\": \"Multiple status values can be provided with comma separated strings\",\n" +
                "        \"operationId\": \"findPetsByStatus\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"status\",\n" +
                "            \"in\": \"query\",\n" +
                "            \"description\": \"Status values that need to be considered for filter\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"array\",\n" +
                "            \"items\": {\n" +
                "              \"type\": \"string\",\n" +
                "              \"enum\": [\n" +
                "                \"available\",\n" +
                "                \"pending\",\n" +
                "                \"sold\"\n" +
                "              ],\n" +
                "              \"default\": \"available\"\n" +
                "            },\n" +
                "            \"collectionFormat\": \"multi\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"array\",\n" +
                "              \"items\": {\n" +
                "                \"$ref\": \"#/definitions/Pet\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid status value\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"/pet/findByTags\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Finds Pets by tags\",\n" +
                "        \"description\": \"Multiple tags can be provided with comma separated strings. Use tag1, tag2, tag3 for testing.\",\n" +
                "        \"operationId\": \"findPetsByTags\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"tags\",\n" +
                "            \"in\": \"query\",\n" +
                "            \"description\": \"Tags to filter by\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"array\",\n" +
                "            \"items\": {\n" +
                "              \"type\": \"string\"\n" +
                "            },\n" +
                "            \"collectionFormat\": \"multi\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"array\",\n" +
                "              \"items\": {\n" +
                "                \"$ref\": \"#/definitions/Pet\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid tag value\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ],\n" +
                "        \"deprecated\": true\n" +
                "      }\n" +
                "    },\n" +
                "    \"/pet/{petId}\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Find pet by ID\",\n" +
                "        \"description\": \"Returns a single pet\",\n" +
                "        \"operationId\": \"getPetById\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"petId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"ID of pet to return\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"format\": \"int64\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Pet\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid ID supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"Pet not found\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"api_key\": []\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Updates a pet in the store with form data\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"updatePetWithForm\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/x-www-form-urlencoded\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"petId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"ID of pet that needs to be updated\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"format\": \"int64\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"name\",\n" +
                "            \"in\": \"formData\",\n" +
                "            \"description\": \"Updated name of the pet\",\n" +
                "            \"required\": false,\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"status\",\n" +
                "            \"in\": \"formData\",\n" +
                "            \"description\": \"Updated status of the pet\",\n" +
                "            \"required\": false,\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"405\": {\n" +
                "            \"description\": \"Invalid input\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"delete\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Deletes a pet\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"deletePet\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"api_key\",\n" +
                "            \"in\": \"header\",\n" +
                "            \"required\": false,\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"petId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"Pet id to delete\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"format\": \"int64\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid ID supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"Pet not found\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"/store/order\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"store\"\n" +
                "        ],\n" +
                "        \"summary\": \"Place an order for a pet\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"placeOrder\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"order placed for purchasing the pet\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Order\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Order\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid Order\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/store/order/{orderId}\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"store\"\n" +
                "        ],\n" +
                "        \"summary\": \"Find purchase order by ID\",\n" +
                "        \"description\": \"For valid response try integer IDs with value >= 1 and <= 10. Other values will generated exceptions\",\n" +
                "        \"operationId\": \"getOrderById\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"orderId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"ID of pet that needs to be fetched\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"maximum\": 10,\n" +
                "            \"minimum\": 1,\n" +
                "            \"format\": \"int64\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Order\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid ID supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"Order not found\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"delete\": {\n" +
                "        \"tags\": [\n" +
                "          \"store\"\n" +
                "        ],\n" +
                "        \"summary\": \"Delete purchase order by ID\",\n" +
                "        \"description\": \"For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors\",\n" +
                "        \"operationId\": \"deleteOrder\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"orderId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"ID of the order that needs to be deleted\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"minimum\": 1,\n" +
                "            \"format\": \"int64\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid ID supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"Order not found\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/store/inventory\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"store\"\n" +
                "        ],\n" +
                "        \"summary\": \"Returns pet inventories by status\",\n" +
                "        \"description\": \"Returns a map of status codes to quantities\",\n" +
                "        \"operationId\": \"getInventory\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"object\",\n" +
                "              \"additionalProperties\": {\n" +
                "                \"type\": \"integer\",\n" +
                "                \"format\": \"int32\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"api_key\": []\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user/createWithArray\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Creates list of users with given input array\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"createUsersWithArrayInput\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"List of user object\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"array\",\n" +
                "              \"items\": {\n" +
                "                \"$ref\": \"#/definitions/User\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"default\": {\n" +
                "            \"description\": \"successful operation\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user/createWithList\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Creates list of users with given input array\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"createUsersWithListInput\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"List of user object\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"array\",\n" +
                "              \"items\": {\n" +
                "                \"$ref\": \"#/definitions/User\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"default\": {\n" +
                "            \"description\": \"successful operation\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user/{username}\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Get user by user name\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"getUserByName\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"username\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"The name that needs to be fetched. Use user1 for testing. \",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/User\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid username supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"User not found\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"put\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Updated user\",\n" +
                "        \"description\": \"This can only be done by the logged in user.\",\n" +
                "        \"operationId\": \"updateUser\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"username\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"name that need to be updated\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"Updated user object\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/User\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid user supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"User not found\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"delete\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Delete user\",\n" +
                "        \"description\": \"This can only be done by the logged in user.\",\n" +
                "        \"operationId\": \"deleteUser\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"username\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"The name that needs to be deleted\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid username supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"User not found\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user/login\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Logs user into the system\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"loginUser\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"username\",\n" +
                "            \"in\": \"query\",\n" +
                "            \"description\": \"The user name for login\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"password\",\n" +
                "            \"in\": \"query\",\n" +
                "            \"description\": \"The password for login in clear text\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"headers\": {\n" +
                "              \"X-Expires-After\": {\n" +
                "                \"type\": \"string\",\n" +
                "                \"format\": \"date-time\",\n" +
                "                \"description\": \"date in UTC when token expires\"\n" +
                "              },\n" +
                "              \"X-Rate-Limit\": {\n" +
                "                \"type\": \"integer\",\n" +
                "                \"format\": \"int32\",\n" +
                "                \"description\": \"calls per hour allowed by the user\"\n" +
                "              }\n" +
                "            },\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"string\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid username/password supplied\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user/logout\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Logs out current logged in user session\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"logoutUser\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [],\n" +
                "        \"responses\": {\n" +
                "          \"default\": {\n" +
                "            \"description\": \"successful operation\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Create user\",\n" +
                "        \"description\": \"This can only be done by the logged in user.\",\n" +
                "        \"operationId\": \"createUser\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"Created user object\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/User\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"default\": {\n" +
                "            \"description\": \"successful operation\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"securityDefinitions\": {\n" +
                "    \"api_key\": {\n" +
                "      \"type\": \"apiKey\",\n" +
                "      \"name\": \"api_key\",\n" +
                "      \"in\": \"header\"\n" +
                "    },\n" +
                "    \"petstore_auth\": {\n" +
                "      \"type\": \"oauth2\",\n" +
                "      \"authorizationUrl\": \"https://petstore.swagger.io/oauth/authorize\",\n" +
                "      \"flow\": \"implicit\",\n" +
                "      \"scopes\": {\n" +
                "        \"read:pets\": \"read your pets\",\n" +
                "        \"write:pets\": \"modify pets in your account\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"definitions\": {\n" +
                "    \"ApiResponse\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"code\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int32\"\n" +
                "        },\n" +
                "        \"type\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"message\": {\n" +
                "          \"type\": \"string\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"Category\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "          \"type\": \"string\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"xml\": {\n" +
                "        \"name\": \"Category\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"Pet\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"required\": [\n" +
                "        \"name\",\n" +
                "        \"photoUrls\"\n" +
                "      ],\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"category\": {\n" +
                "          \"$ref\": \"#/definitions/Category\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"example\": \"doggie\"\n" +
                "        },\n" +
                "        \"photoUrls\": {\n" +
                "          \"type\": \"array\",\n" +
                "          \"xml\": {\n" +
                "            \"wrapped\": true\n" +
                "          },\n" +
                "          \"items\": {\n" +
                "            \"type\": \"string\",\n" +
                "            \"xml\": {\n" +
                "              \"name\": \"photoUrl\"\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        \"tags\": {\n" +
                "          \"type\": \"array\",\n" +
                "          \"xml\": {\n" +
                "            \"wrapped\": true\n" +
                "          },\n" +
                "          \"items\": {\n" +
                "            \"xml\": {\n" +
                "              \"name\": \"tag\"\n" +
                "            },\n" +
                "            \"$ref\": \"#/definitions/Tag\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"status\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"description\": \"pet status in the store\",\n" +
                "          \"enum\": [\n" +
                "            \"available\",\n" +
                "            \"pending\",\n" +
                "            \"sold\"\n" +
                "          ]\n" +
                "        }\n" +
                "      },\n" +
                "      \"xml\": {\n" +
                "        \"name\": \"Pet\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"Tag\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "          \"type\": \"string\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"xml\": {\n" +
                "        \"name\": \"Tag\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"Order\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"petId\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"quantity\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int32\"\n" +
                "        },\n" +
                "        \"shipDate\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"format\": \"date-time\"\n" +
                "        },\n" +
                "        \"status\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"description\": \"Order Status\",\n" +
                "          \"enum\": [\n" +
                "            \"placed\",\n" +
                "            \"approved\",\n" +
                "            \"delivered\"\n" +
                "          ]\n" +
                "        },\n" +
                "        \"complete\": {\n" +
                "          \"type\": \"boolean\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"xml\": {\n" +
                "        \"name\": \"Order\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"User\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"username\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"firstName\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"lastName\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"email\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"password\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"phone\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"userStatus\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int32\",\n" +
                "          \"description\": \"User Status\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"xml\": {\n" +
                "        \"name\": \"User\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"externalDocs\": {\n" +
                "    \"description\": \"Find out more about Swagger\",\n" +
                "    \"url\": \"http://swagger.io\"\n" +
                "  }\n" +
                "}");
        System.out.println();
    }
}