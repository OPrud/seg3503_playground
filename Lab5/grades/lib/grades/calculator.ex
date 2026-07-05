defmodule Grades.Calculator do
  @homework_weight 0.20
  @labs_weight 0.20
  @midterm_weight 0.25
  @final_weight 0.35

  def percentage_grade(grades) do
    homework_avg = average(grades.homework)
    labs_avg = average(grades.labs)
    midterm = to_number(grades.midterm)
    final = to_number(grades.final)

    total =
      homework_avg * @homework_weight +
        labs_avg * @labs_weight +
        midterm * @midterm_weight +
        final * @final_weight

    Float.round(total, 1)
  end

  def letter_grade(grades) do
    grades
    |> percentage_grade()
    |> to_letter()
  end

  def numeric_grade(grades) do
    grades
    |> percentage_grade()
    |> to_numeric()
  end

  defp average([]), do: 0.0

  defp average(list) do
    numbers = list |> Enum.map(&to_number/1)
    count = length(numbers)

    if count == 0 do
      0.0
    else
      Enum.sum(numbers) / count
    end
  end

  defp to_number(nil), do: 0.0
  defp to_number(""), do: 0.0

  defp to_number(value) when is_binary(value) do
    case Float.parse(value) do
      {num, _rest} -> num
      :error -> 0.0
    end
  end

  defp to_number(value) when is_number(value), do: value * 1.0

  defp to_letter(pct) when pct >= 90, do: "A+"
  defp to_letter(pct) when pct >= 85, do: "A"
  defp to_letter(pct) when pct >= 80, do: "A-"
  defp to_letter(pct) when pct >= 77, do: "B+"
  defp to_letter(pct) when pct >= 73, do: "B"
  defp to_letter(pct) when pct >= 70, do: "B-"
  defp to_letter(pct) when pct >= 67, do: "C+"
  defp to_letter(pct) when pct >= 63, do: "C"
  defp to_letter(pct) when pct >= 60, do: "C-"
  defp to_letter(pct) when pct >= 55, do: "D+"
  defp to_letter(pct) when pct >= 50, do: "D"
  defp to_letter(_pct), do: "F"

  defp to_numeric(pct) when pct >= 90, do: 10.0
  defp to_numeric(pct) when pct >= 85, do: 9.0
  defp to_numeric(pct) when pct >= 80, do: 8.0
  defp to_numeric(pct) when pct >= 77, do: 7.5
  defp to_numeric(pct) when pct >= 73, do: 7.0
  defp to_numeric(pct) when pct >= 70, do: 6.5
  defp to_numeric(pct) when pct >= 67, do: 6.0
  defp to_numeric(pct) when pct >= 63, do: 5.5
  defp to_numeric(pct) when pct >= 60, do: 5.0
  defp to_numeric(pct) when pct >= 55, do: 4.5
  defp to_numeric(pct) when pct >= 50, do: 4.0
  defp to_numeric(_pct), do: 0.0
end