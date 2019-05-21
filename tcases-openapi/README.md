# Tcases for OpenAPI: From REST-ful to Test-ful  #

## Contents ##

  - [Using OpenAPI to model your API? ](#using-openapi-to-model-your-api)
  - [Then use Tcases to generate your API test cases ](#then-use-tcases-to-generate-your-api-test-cases)
  - [Why Tcases for OpenAPI? ](#why-tcases-for-openapi)
  - [Is your OpenAPI spec an input model? No, it's two! ](#is-your-openapi-spec-an-input-model-no-its-two)
  - [Running Tcases for OpenAPI from the command line ](#running-tcases-for-openapi-from-the-command-line)
  - [Using the Java API for Tcases for OpenAPI ](#using-the-java-api-for-tcases-for-openapi)
  - [OpenAPI tips ](#openapi-tips)
  - [Test case generation tips ](#test-case-generation-tips)


## Using OpenAPI to model your API? ##

If you are developing REST-ful APIs, you probably know about the [OpenAPI
Specification](https://github.com/OAI/OpenAPI-Specification).  The OpenAPI Specification (OAS) defines a standard,
programming language-agnostic interface description for REST APIs, which allows both humans and computers to discover
and understand the capabilities of a service. OpenAPI documents describe an API's services and are represented in either
YAML or JSON formats.

You probably know that modeling your API with an OpenAPI specification has many benefits. There are lots of tools that can use an OpenAPI document
to generate interactive documentation, code stubs (for either the client or the server), and test mocks. But did you know you can also generate test cases for your
API directly from your OpenAPI specification? You do now.

## Then use Tcases to generate your API test cases ##

Consider the standard example for an OpenAPI (Version 3) specification: the [Pet Store
API](https://github.com/OAI/OpenAPI-Specification/blob/master/examples/v3.0/petstore-expanded.yaml). This defines all of
the requests and responses for an API to access the resources of the Swagger Pet Store.

Suppose you wanted to test this API. What test cases would you need? To find out, run this command:

```
tcases-api petstore-expanded.yaml
```

OK, so what just happened? Take a look at the `tcases-api.log` file, and you'll see something like this:

```
13:06:15.942 INFO  o.c.tcases.openapi.ApiCommand - 3.1.0
13:06:15.991 INFO  o.c.tcases.openapi.ApiCommand - Reading API spec from ./petstore-expanded.yaml
13:06:16.300 INFO  o.c.tcases.openapi.ApiCommand - Writing results to ././petstore-expanded-Requests-Test.json
...
13:06:16.374 INFO  o.c.t.generator.TupleGenerator - FunctionInputDef[GET_pets]: Completed 11 test cases
...
13:06:16.397 INFO  o.c.t.generator.TupleGenerator - FunctionInputDef[POST_pets]: Completed 12 test cases
...
13:06:16.403 INFO  o.c.t.generator.TupleGenerator - FunctionInputDef[GET_pets-id]: Completed 6 test cases
...
13:06:16.407 INFO  o.c.t.generator.TupleGenerator - FunctionInputDef[DELETE_pets-id]: Completed 6 test cases
```

Nice! A total of 35 test cases were generated for the 4 requests in this API. To review them, take a look at the
`petstore-expanded-Requests-Test.json` file, and you'll see something like what's shown below.

It's telling you that, for starters, you should have a test case for the `GET /pets` request that supplies two query
parameters, setting the `tags` parameter to an empty array and the `limit` parameter to a negative 32-bit integer. For
this particular test case, because `tags` is empty, other aspects of this array are irrelevant and are designated as
"not applicable" (`NA`). For a complete explanation of this JSON format for tests case definitions, see [*Tcases: The
JSON Guide*](http://www.cornutum.org/tcases/docs/Tcases-Json.htm#testCases).

```
{
  "system": "Swagger-Petstore",
  "has": {
    "server": "http://petstore.swagger.io/api",
    "version": "1.0.0"
  },
  "GET_pets": {
    "has": {
      "server": "http://petstore.swagger.io/api",
      "version": "1.0.0"
    },
    "testCases": [
      {
        "id": 0,
        "has": {
          "server": "http://petstore.swagger.io/api",
          "version": "1.0.0",
          "properties": "limit,limitValue,tags,tagsValue"
        },
        "query": {
        "tags.Defined": {
            "has": {
              "explode": "true",
              "style": "form"
            },
            "value": "Yes"
          },
          "tags.Type": {
            "value": "array"
          },
          "tags.Items.Size": {
            "value": "0"
          },
          "tags.Items.Contains.Type": {
            "NA": true
          },
          "tags.Items.Contains.Value.Length": {
            "NA": true
          },
          "tags.Items.Unique": {
            "NA": true
          },
          "limit.Defined": {
            "has": {
              "style": "form"
            },
            "value": "Yes"
          },
          "limit.Type": {
            "value": "integer"
          },
          "limit.Value": {
            "has": {
              "format": "int32"
            },
            "value": "< 0"
          }
        }
      },
      ...
    ]
  },
  "POST_pets": {
    "has": {
      "server": "http://petstore.swagger.io/api",
      "version": "1.0.0"
    },
    "testCases": [
      ...
    ]
  },
  "GET_pets-id": {
    "has": {
      "server": "http://petstore.swagger.io/api",
      "version": "1.0.0"
    },
    "testCases": [
      ...
    ]
  },
  "DELETE_pets-id": {
    "has": {
      "server": "http://petstore.swagger.io/api",
      "version": "1.0.0"
    },
    "testCases": [
      ...
    ]
  }
}
```

## Why Tcases for OpenAPI? ##

### Want high confidence in your API? ##

[Tcases](https://github.com/Cornutum/tcases) is a tool that generates test cases for 100% coverage of the input space of
your system. In other words, when applied to your OpenAPI specification, the test cases generated by Tcases provide 100%
coverage of the input space of each API request.  If you want tests that will give you high confidence in your API,
that's a pretty good way to go.

But what is the "input space" for a request? And what exactly does this coverage mean? 100% of what?

Good questions. And to fully understand the answers, you need to know more about how Tcases [models system
inputs](http://www.cornutum.org/tcases/docs/Tcases-Guide.htm#input) and how Tcases [measures
coverage](http://www.cornutum.org/tcases/docs/Tcases-Guide.htm#coverage). But here's the short answer: for every
request, these test cases ensure that every key variation in the value for every input parameter is used at least
once. That includes not only valid values, which are expected to produce a successful result, but also invalid values,
which are expected to produce some kind of failure response.

Why is this kind of "each-choice" coverage helpful? Because it is quite likely to expose most of the defects that may be
lurking in your API.  In fact, if you measure how well these tests cover the code used to implement API requests, you
are likely to find that they cover more than 75% of the branches in the code.

### Want high confidence in your OpenAPI spec? ##

An OpenAPI specification for your API can be a tricky thing to get right. But Tcases for OpenAPI can help. It will tell
you about any elements in your spec that don't comply with the OAS (Version 3) standard. But that's not all. It will
also warn you about elements in your spec that, while technically compliant, may actually be inconsistent or superfluous
or may not behave as you expected.

But that's not all. Even when your OpenAPI spec is valid, consistent, and correct it still may not describe how your API
*actually works*! And Tcases for OpenAPI can help show you when this happens. The Pet Store example shown above is a
good illustration. For the `GET /pets` request, Tcases for OpenAPI suggests a test case in which the `limit` parameter
is a negative integer. That's weird. Just guessing, but isn't it likely that negative values for `limit` are not really
meaningful?  But the spec didn't say that! Could it be that there's a missing `minimum` keyword in the schema for the
`limit` parameter? Thank you, Tcases for OpenAPI!

It can be hard to avoid unconscious assumptions about your API and how it will be used by its clients. That can lead to
a faulty OpenAPI spec that is either too tight or too loose, when compared with the actual behavior of your API and its
clients. But Tcases for OpenAPI makes no such assumptions. It relentlessly covers the input space that your OpenAPI spec
actually defines. If you find that some of these test cases are surprising, you may find an opportunity to make your
OpenAPI spec even better.

## Is your OpenAPI spec an input model? No, it's two! ##

There are two sides to your API: the requests and their responses. Both are defined in an OpenAPI spec. But which side
is the "input space"? It depends on your perspective. For the API server, requests are the inputs and responses are the
outputs. For an API client, it's the other way around: input to the client comes from the responses to requests that are
sent out to the server.

Accordingly, Tcases for OpenAPI generates tests cases for each side separately. Run Tcases for OpenAPI from the server
perspective and it will generate test cases that cover inputs to the server from request parameters. Run Tcases for
OpenAPI from the client perspective and it will generate test cases that cover inputs to the client from request
responses. For complete testing, both perspectives are needed. Why? Because, based on the OpenAPI spec alone, there is
no guarantee that 100% coverage of one side will produce 100% coverage of the other.

## Running Tcases for OpenAPI from the command line ##

You can run Tcases for OpenAPI directly from your shell command line. It's an easy way to examine your OpenAPI spec and
to investigate the tests that it generates. If you use `bash` or a similar UNIX shell, you can run the `tcases-api` command. Or
if you are using a Windows command line, you can run Tcases for OpenAPI with the `tcases-api.bat` command file, using exactly the same
syntax.

`tcase-api` is included in the Tcases binary distribution file. For instructions on how to download and install it, see
[*Tcases: The Complete Guide*](http://www.cornutum.org/tcases/docs/Tcases-Guide.htm#install).

For details about the interface to the `tcases-api` command (and the `tcases-api.bat` command, too), see the Javadoc for
the [`ApiCommand.Options`](http://www.cornutum.org/tcases/docs/api/org/cornutum/tcases/openapi/ApiCommand.Options.html)
class.  To get help at the command line, run `tcases-api -help`.

Examples:

```bash
# Generate tests for requests defined in 'my-api.json'. Write results to 'my-api-Request-Tests.json'.
tcases-api my-api.json
```


```bash
# Generate tests for responses defined in 'my-api.json'. Write results to 'otherDir/someTests.json'.
tcases-api -C -f someTests.json -o otherDir my-api.json
```


```bash
# Generate tests for requests defined in the spec read from standard input. (JSON format assumed!)
# Write results to standard output, unless an input modelling condition is found.
# (See 'Test case generation tips'.)
tcases-api -S -c fail < my-api.json
```

## Using the Java API for Tcases for OpenAPI ##

You can also run Tcases for OpenAPI by integrating it into your own Java application. If you are processing OpenAPI
specs in the form of files or IO streams, you can use the methods of the
[`TcasesOpenApiIO`](http://www.cornutum.org/tcases/docs/api/org/cornutum/tcases/openapi/io/TcasesOpenApiIO.html) class
to generate input and test models from either the server or client perspective. These methods are based on lower-level
classes like [`TcasesOpenApi`](http://www.cornutum.org/tcases/docs/api/org/cornutum/tcases/openapi/TcasesOpenApi.html),
which operate directly with the [Java API for OpenAPI](https://github.com/swagger-api/swagger-parser). In turn, both
classes are based on the [Tcases Core API](http://www.cornutum.org/tcases/docs/api/) for manipulating system and test
models.

## OpenAPI tips ##

When you are using Tcases for OpenAPI, here are some things to keep in mind when you're developing your OpenAPI spec.

  1. **Use Version 3**: Tcases for OpenAPI is based on the specification for [OpenAPI Version 3.0.2](https://swagger.io/specification/). Earlier Version 2.X specs are not supported.

  1. **Avoid type-ambiguous schemas**: Tcases for OpenAPI does not accept schema definitions that are "type-ambiguous". A type-ambiguous schema is one that does not define an explicit `type` keyword but includes other keywords that apply to multiple types. (For example, a `minLength` keyword, which applies only to `string` values, together with an `items` keyword, which applies only to `array` values.) Although type-ambiguous schemas are allowed in OpenAPI, they imply a very large and complex input space. (Probably much more than was actually intended!). Fortunately, it's easy to avoid them. In cases where different types of values are actually expected, you can define this explicitly using `oneOf` or `anyOf` keywords.


## Test case generation tips ##

  1. **Handle input modelling conditions**: Tcases for OpenAPI reports conditions in your OpenAPI spec that will affect how test cases are generated. Warning conditions are reported with an explanation of the situation. Error conditions report elements in your spec that may need to be changed to generate tests correctly. By default, conditions are reported by writing log messages. By specifying a different [`ModelConditionNotifier`](http://www.cornutum.org/tcases/docs/api/org/cornutum/tcases/openapi/ModelConditionNotifier.html), you can cause these conditions to throw an exception or to be completely ignored. You can do this at the command line using the `-c` option of the `tcases-api` command. You can even customize condition handling using your own implementation of the `ModelConditionNotifier` interface in the [`ModelOptions`](http://www.cornutum.org/tcases/docs/api/org/cornutum/tcases/openapi/ModelOptions.html) used by Tcases for OpenAPI.

  1. **Handle "readOnly" and "writeOnly" properties**: The OpenAPI standard defines how you can identify data object properties as ["readOnly" or "writeOnly"](https://swagger.io/specification/#schemaObject). Strict enforcement of "readOnly" or "writeOnly" properties is not required. But how your API handles these such properties will affect your test cases. By default, Tcases for OpenAPI generates tests assuming that optional "readOnly" and "writeOnly" restrictions are not enforced. But you can change the [`ModelOptions`](http://www.cornutum.org/tcases/docs/api/org/cornutum/tcases/openapi/ModelOptions.html) to assume strict enforcement when generating tests cases. You can do this at the command line with the `-R` and `-W` options of the `tcases-api` command.
  