;; Using http://mbebenita.github.io/WasmExplorer/ to convert to wasm

(module
  (table 0 anyfunc)
  (memory $0 1)
  (export "memory" (memory $0))
  (export "i32Sub" (func $i32Sub))
  (func $i32Equal (param $0 i32) (param $1 i32) (result i32)
    (i32.sub
      (get_local $1)
      (get_local $0)
    )
  )
)