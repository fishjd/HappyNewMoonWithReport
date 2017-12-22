(module
  (table 0 anyfunc)
  (memory $0 1)
  (export "memory" (memory $0))
  (export "factorialLong" (func $factorialLong))
  (func $factorialLong (param $0 i32) (result i32)
    (local $1 i32)
    // declare 'n'  thus $2 is 'n'
    (local $2 i32)
    //  Line 11: long n = 1;
    (set_local $2
      (i32.const 1)
    )
    (block $label$0
      (br_if $label$0
        (i32.lt_s
          (get_local $0)
          (i32.const 1)
        )
      )
      (set_local $2
        (i32.const 1)
      )
      (loop $label$1
        (set_local $2
          (i32.mul
            (get_local $2)
            (get_local $0)
          )
        )
        (set_local $1
          (i32.gt_s
            (get_local $0)
            (i32.const 1)
          )
        )
        (set_local $0
          (i32.add
            (get_local $0)
            (i32.const -1)
          )
        )
        (br_if $label$1
          (get_local $1)
        )
      )
    )
    (get_local $2)
  )
)
