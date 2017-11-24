package game


class Leaderboards(filename: String) {
  private var entries: Seq[Score] = Disk.loadLeaderboards(filename)
  
  def add(score: Score): Unit = {
    entries = entries :+ score
    Disk.saveLeaderboards(filename, entries)
  }
  
  def top(n: Int): Seq[Score] = entries.sortBy(-_.score).take(n)
  def top(n: Int, player: String) = entries.filter(_.name == player).sortBy(-_.score).take(n)
  
  def printTop(n: Int) {
    println("LEADERBOARDS")
    for((Score(name, score), i) <- top(n).zipWithIndex) {
      println(s"$i. $name - $score seconds")
    }
  }
}
