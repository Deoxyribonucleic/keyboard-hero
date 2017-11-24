package game


class Leaderboards(filename: String) {
  private var entries: Seq[Score] = Disk.loadLeaderboards(filename)
  
  def add(score: Score): Unit = {
    entries = entries :+ score
    Disk.saveLeaderboards(filename, entries)
  }
  
  def top(n: Int): Seq[Score] = entries.sortBy(-_.score).take(n)
  def top(n: Int, player: String) = entries.filter(_.name == player).sortBy(-_.score).take(n)
  
  def print(entries: Seq[Score]) {
    println("LEADERBOARDS\n")
    for((Score(name, score), i) <- entries.zipWithIndex) {
      println(s"${i + 1}. $name - $score seconds")
    }
  }
}
