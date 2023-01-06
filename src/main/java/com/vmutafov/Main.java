package com.vmutafov;

import org.graalvm.polyglot.Context;

public class Main {
    public static void main(String[] args) {
        var context = Context
                .newBuilder("js")
                .option("js.ecmascript-version", "staging")
                .option("engine.WarnInterpreterOnly", "false")
                .build();

var js = """
        
        function logged(value, { kind, name }) {
          if (kind === "method") {
            return function (...args) {
              console.log(`Calling ${name} with arguments ${args.join(", ")}`);
              return value.call(this, ...args);
            };
          }
        }
                        
        class DecoratedClass {
          @logged
          test(arg) {}
        }
                        
        const decorated = new DecoratedClass();
        decorated.test(123);
        
        """;

        context.eval("js", js);
    }
}
