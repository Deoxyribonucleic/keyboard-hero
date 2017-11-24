package game


object Disk {
  def saveLeaderboards(filename: String, entries: Seq[Score]) {
    try {
      val writer = new java.io.PrintWriter(new java.io.File(filename));
      
      for (Score(name, score) <- entries) {
        writer.println(s"${name.filterNot(_.isWhitespace)} $score")
      }
      
      writer.close();
    } catch {
      case e: Exception => println("Failed to save leaderboards: " + e)
    }
  }
  
  def loadLeaderboards(filename: String): Seq[Score] = {
    var entries: Seq[Score] = List()
    
    try {
      val sc = new java.util.Scanner(new java.io.File(filename));
      sc.useLocale(java.util.Locale.US);
      
      while (sc.hasNext()) {
        val name = sc.next()
        val score = sc.nextFloat();
        entries = entries :+ Score(name, score)
      }
    } catch {
      case e: Exception => println("Failed to load leaderboards: " + e);
    }
    
    entries
  }
}
