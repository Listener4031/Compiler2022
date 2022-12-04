define void @print(i8* %0)
define void @println(i8* %0)
define void @printInt(i32 %0)
define void @printlnInt(i32 %0)
define i8* @getString()
define i32 @getInt()
define i8* @toString(i32 %0)
define i32 @length(i8* %0)
define i8* @substring(i8* %0, i32 %1, i32 %2)
define i32 @parseInt(i8* %0)
define i32 @ord(i8* %0)
define void @_global() {
_global_allocate:
  br label %_global_entry

_global_entry:
  br label %_global_return

_global_return:
  ret void
}
define i32 @main() {
main_allocate:
  %0 = alloca i32
  %1 = alloca i32
  %2 = alloca i32
  %7 = alloca i32
  %12 = alloca i32
  %17 = alloca i32
  %22 = alloca i32
  %27 = alloca i32
  %32 = alloca i32
  %37 = alloca i8
  %42 = alloca i8
  %47 = alloca i8
  %52 = alloca i8
  %57 = alloca i8
  %62 = alloca i8
  %67 = alloca i32
  %72 = alloca i32
  %77 = alloca i32
  br label %main_entry

main_entry:
  store i32 0, i32* %0
  store i32 2, i32* %1
  %3 = load i32, i32* %1
  %4 = load i32, i32* %1
  %5 = mul i32 %3, %4
  store i32 %6, i32* %2
  %8 = load i32, i32* %1
  %9 = load i32, i32* %1
  %10 = sdiv i32 %8, %9
  store i32 %11, i32* %7
  %13 = load i32, i32* %1
  %14 = load i32, i32* %1
  %15 = srem i32 %13, %14
  store i32 %16, i32* %12
  %18 = load i32, i32* %1
  %19 = load i32, i32* %1
  %20 = add i32 %18, %19
  store i32 %21, i32* %17
  %23 = load i32, i32* %1
  %24 = load i32, i32* %1
  %25 = sub i32 %23, %24
  store i32 %26, i32* %22
  %28 = load i32, i32* %1
  %29 = load i32, i32* %1
  %30 = shl i32 %28, %29
  store i32 %31, i32* %27
  %33 = load i32, i32* %1
  %34 = load i32, i32* %1
  %35 = ashr i32 %33, %34
  store i32 %36, i32* %32
  %38 = load i32, i32* %1
  %39 = load i32, i32* %2
  %40 = icmp slt i1 %38, %39
  %41 = zext i1 %40 to i8
  store i8 %41, i8* %37
  %43 = load i32, i32* %1
  %44 = load i32, i32* %2
  %45 = icmp sle i1 %43, %44
  %46 = zext i1 %45 to i8
  store i8 %46, i8* %42
  %48 = load i32, i32* %1
  %49 = load i32, i32* %2
  %50 = icmp sgt i1 %48, %49
  %51 = zext i1 %50 to i8
  store i8 %51, i8* %47
  %53 = load i32, i32* %1
  %54 = load i32, i32* %2
  %55 = icmp sge i1 %53, %54
  %56 = zext i1 %55 to i8
  store i8 %56, i8* %52
  %58 = load i32, i32* %1
  %59 = load i32, i32* %2
  %60 = icmp eq i1 %58, %59
  %61 = zext i1 %60 to i8
  store i8 %61, i8* %57
  %63 = load i32, i32* %1
  %64 = load i32, i32* %2
  %65 = icmp ne i1 %63, %64
  %66 = zext i1 %65 to i8
  store i8 %66, i8* %62
  %68 = load i32, i32* %1
  %69 = load i32, i32* %2
  %70 = and i32 %68, %69
  store i32 %71, i32* %67
  %73 = load i32, i32* %1
  %74 = load i32, i32* %2
  %75 = xor i32 %73, %74
  store i32 %76, i32* %72
  %78 = load i32, i32* %1
  %79 = load i32, i32* %2
  %80 = or i32 %78, %79
  store i32 %81, i32* %77
  store i32 3, i32* %0
  br label %main_return
  br label %main_return

main_return:
  %82 = load i32, i32* %0
  ret i32 %82
}
