; ModuleID = 'test1.cpp'
source_filename = "test1.cpp"
target datalayout = "e-m:o-i64:64-i128:128-n32:64-S128"
target triple = "arm64-apple-macosx11.0.0"

@global1 = global i32 1, align 4
@global2 = global i32 0, align 4
@llvm.global_ctors = appending global [1 x { i32, void ()*, i8* }] [{ i32, void ()*, i8* } { i32 65535, void ()* @_GLOBAL__sub_I_test1.cpp, i8* null }]

; Function Attrs: noinline ssp uwtable
define internal void @__cxx_global_var_init() #0 section "__TEXT,__StaticInit,regular,pure_instructions" {
  %1 = load i32, i32* @global1, align 4
  %2 = sub nsw i32 %1, 1
  store i32 %2, i32* @global2, align 4
  ret void
}

; Function Attrs: noinline norecurse nounwind optnone ssp uwtable
define i32 @main() #1 {
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  store i32 0, i32* %1, align 4
  store i32 2, i32* %2, align 4
  ret i32 3
}

; Function Attrs: noinline ssp uwtable
define internal void @_GLOBAL__sub_I_test1.cpp() #0 section "__TEXT,__StaticInit,regular,pure_instructions" {
  call void @__cxx_global_var_init()
  ret void
}

attributes #0 = { noinline ssp uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "frame-pointer"="non-leaf" "less-precise-fpmad"="false" "min-legal-vector-width"="0" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="apple-a12" "target-features"="+aes,+crc,+crypto,+fp-armv8,+fullfp16,+lse,+neon,+ras,+rcpc,+rdm,+sha2,+v8.3a,+zcm,+zcz" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { noinline norecurse nounwind optnone ssp uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "frame-pointer"="non-leaf" "less-precise-fpmad"="false" "min-legal-vector-width"="0" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="true" "probe-stack"="__chkstk_darwin" "stack-protector-buffer-size"="8" "target-cpu"="apple-a12" "target-features"="+aes,+crc,+crypto,+fp-armv8,+fullfp16,+lse,+neon,+ras,+rcpc,+rdm,+sha2,+v8.3a,+zcm,+zcz" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.module.flags = !{!0, !1, !2}
!llvm.ident = !{!3}

!0 = !{i32 2, !"SDK Version", [2 x i32] [i32 11, i32 3]}
!1 = !{i32 1, !"wchar_size", i32 4}
!2 = !{i32 7, !"PIC Level", i32 2}
!3 = !{!"Apple clang version 12.0.5 (clang-1205.0.22.11)"}
