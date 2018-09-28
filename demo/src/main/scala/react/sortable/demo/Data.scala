package react.sortable.demo

import scala.util.Random._
import scala.scalajs.js

object Data {
  trait DataRow extends js.Object {
    var color: String
    var index: Int
    var name: String
    var random: String
    var randomLong: String
    var size: Int
  }
  object DataRow {
    def apply(color: String, index: Int, name: String, random: String, randomLong: String, size: Int): DataRow = {
      val p = (new js.Object).asInstanceOf[DataRow]
      p.color = color
      p.index = index
      p.name = name
      p.random = random
      p.randomLong = randomLong
      p.size = size
      p
    }
  }
  def generateRandomList: List[DataRow] =
    (for {
      i <- 0 to 1000
    } yield {
      def rnd = LoremIpsum(i % LoremIpsum.length)
      val randoms = (0 to nextInt(10)).foldLeft("") { (ac, _) =>
        ac + rnd
      }
      DataRow(BadgeColors(i % BadgeColors.length), i, Names(i % Names.length), rnd, randoms, RowHeights(i % RowHeights.length))
    }).toList

  val BadgeColors = List(
    "#f44336",
    "#3f51b5",
    "#4caf50",
    "#ff9800",
    "#2196f3",
    "#374046",
    "#cddc39",
    "#2196f3",
    "#9c27b0",
    "#ffc107",
    "#009688",
    "#673ab7",
    "#ffeb3b",
    "#cddc39",
    "#795548"
  )
  val Names = List(
    "Peter Brimer",
    "Tera Gaona",
    "Kandy Liston",
    "Lonna Wrede",
    "Kristie Yard",
    "Raul Host",
    "Yukiko Binger",
    "Velvet Natera",
    "Donette Ponton",
    "Loraine Grim",
    "Shyla Mable",
    "Marhta Sing",
    "Alene Munden",
    "Holley Pagel",
    "Randell Tolman",
    "Wilfred Juneau",
    "Naida Madson",
    "Marine Amison",
    "Glinda Palazzo",
    "Lupe Island",
    "Cordelia Trotta",
    "Samara Berrier",
    "Era Stepp",
    "Malka Spradlin",
    "Edward Haner",
    "Clemencia Feather",
    "Loretta Rasnake",
    "Dana Hasbrouck",
    "Sanda Nery",
    "Soo Reiling",
    "Apolonia Volk",
    "Liliana Cacho",
    "Angel Couchman",
    "Yvonne Adam",
    "Jonas Curci",
    "Tran Cesar",
    "Buddy Panos",
    "Rosita Ells",
    "Rosalind Tavares",
    "Renae Keehn",
    "Deandrea Bester",
    "Kelvin Lemmon",
    "Guadalupe Mccullar",
    "Zelma Mayers",
    "Laurel Stcyr",
    "Edyth Everette",
    "Marylin Shevlin",
    "Hsiu Blackwelder",
    "Mark Ferguson",
    "Winford Noggle",
    "Shizuko Gilchrist",
    "Roslyn Cress",
    "Nilsa Lesniak",
    "Agustin Grant",
    "Earlie Jester",
    "Libby Daigle",
    "Shanna Maloy",
    "Brendan Wilken",
    "Windy Knittel",
    "Alice Curren",
    "Eden Lumsden",
    "Klara Morfin",
    "Sherryl Noack",
    "Gala Munsey",
    "Stephani Frew",
    "Twana Anthony",
    "Mauro Matlock",
    "Claudie Meisner",
    "Adrienne Petrarca",
    "Pearlene Shurtleff",
    "Rachelle Piro",
    "Louis Cocco",
    "Susann Mcsweeney",
    "Mandi Kempker",
    "Ola Moller",
    "Leif Mcgahan",
    "Tisha Wurster",
    "Hector Pinkett",
    "Benita Jemison",
    "Kaley Findley",
    "Jim Torkelson",
    "Freda Okafor",
    "Rafaela Markert",
    "Stasia Carwile",
    "Evia Kahler",
    "Rocky Almon",
    "Sonja Beals",
    "Dee Fomby",
    "Damon Eatman",
    "Alma Grieve",
    "Linsey Bollig",
    "Stefan Cloninger",
    "Giovanna Blind",
    "Myrtis Remy",
    "Marguerita Dostal",
    "Junior Baranowski",
    "Allene Seto",
    "Margery Caves",
    "Nelly Moudy",
    "Felix Sailer"
  )
  val RowHeights = List(50, 75, 100)

  val LoremIpsum = List(
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    "Phasellus vulputate odio commodo tortor sodales, et vehicula ipsum viverra.",
    "In et mollis velit, accumsan volutpat libero.",
    "Nulla rutrum tellus ipsum, eget fermentum sem dictum quis.",
    "Suspendisse eget vehicula elit.",
    "Proin ut lacus lacus.",
    "Aliquam erat volutpat.",
    "Vivamus ac suscipit est, et elementum lectus.",
    "Cras tincidunt nisi in urna molestie varius.",
    "Integer in magna eu nibh imperdiet tristique.",
    "Curabitur eu pellentesque nisl.",
    "Etiam non consequat est.",
    "Duis mi massa, feugiat nec molestie sit amet, suscipit et metus.",
    "Curabitur ac enim dictum arcu varius fermentum vel sodales dui.",
    "Ut tristique augue at congue molestie.",
    "Integer semper sem lorem, scelerisque suscipit lacus consequat nec.",
    "Etiam euismod efficitur magna nec dignissim.",
    "Morbi vel neque lectus.",
    "Etiam ac accumsan elit, et pharetra ex.",
    "Suspendisse vitae gravida mauris.",
    "Pellentesque sed laoreet erat.",
    "Nam aliquet purus quis massa eleifend, et efficitur felis aliquam.",
    "Fusce faucibus diam erat, sed consectetur urna auctor at.",
    "Praesent et nulla velit.",
    "Cras eget enim nec odio feugiat tristique eu quis ante.",
    "Morbi blandit diam vitae odio sollicitudin finibus.",
    "Integer ac ante fermentum, placerat orci vel, fermentum lacus.",
    "Maecenas est elit, semper ut posuere et, congue ut orci.",
    "Phasellus eget enim vitae nunc luctus sodales a eu erat.",
    "Curabitur dapibus nisi sed nisi dictum, in imperdiet urna posuere.",
    "Vivamus commodo odio metus, tincidunt facilisis augue dictum quis.",
    "Curabitur sagittis a lectus ac sodales.",
    "Nam eget eros purus.",
    "Nam scelerisque et ante in porta.",
    "Proin vitae augue tristique, malesuada nisl ut, fermentum nisl.",
    "Nulla bibendum quam id velit blandit dictum.",
    "Cras tempus ac dolor ut convallis.",
    "Sed vel ipsum est.",
    "Nulla ut leo vestibulum, ultricies sapien ac, pellentesque dolor.",
    "Etiam ultricies maximus tempus.",
    "Donec dignissim mi ac libero feugiat, vitae lacinia odio viverra.",
    "Curabitur condimentum tellus sit amet neque posuere, condimentum tempus purus eleifend.",
    "Donec tempus, augue id hendrerit pretium, mauris leo congue nulla, ac iaculis erat nunc in dolor.",
    "Praesent vel lectus venenatis, elementum mauris vitae, ullamcorper nulla.",
    "Maecenas non diam cursus, imperdiet massa eget, pellentesque ex.",
    "Vestibulum luctus risus vel augue auctor blandit.",
    "Nullam augue diam, pulvinar sed sapien et, hendrerit venenatis risus.",
    "Quisque sollicitudin nulla nec tellus feugiat hendrerit.",
    "Vestibulum a eros accumsan, lacinia eros non, pretium diam.",
    "Aenean iaculis augue sit amet scelerisque aliquam.",
    "Donec ornare felis et dui hendrerit, eget bibendum nibh interdum.",
    "Maecenas tellus magna, tristique vitae orci vel, auctor tincidunt nisi.",
    "Fusce non libero quis velit porttitor maximus at eget enim.",
    "Sed in aliquet tellus.",
    "Etiam a tortor erat.",
    "Donec nec diam vel tellus egestas lobortis.",
    "Vivamus dictum erat nulla, sit amet accumsan dolor scelerisque eu.",
    "In nec eleifend ex, pellentesque dapibus sapien.",
    "Duis a mollis nisi.",
    "Sed ornare nisl sit amet dolor pellentesque, eu fermentum leo interdum.",
    "Sed eget mauris condimentum, molestie justo eu, feugiat felis.",
    "Nunc suscipit leo non dui blandit, ac malesuada ex consequat.",
    "Morbi varius placerat congue.",
    "Praesent id velit in nunc elementum aliquet.",
    "Sed luctus justo vitae nibh bibendum blandit.",
    "Sed et sapien turpis.",
    "Nulla ac eros vestibulum, mollis ante eu, rutrum nulla.",
    "Sed cursus magna ut vehicula rutrum.",
    "Ut consectetur feugiat consectetur.",
    "Nulla nec ligula posuere neque sollicitudin rutrum a a dui.",
    "Nulla ut quam odio.",
    "Integer dignissim sapien et orci sodales volutpat.",
    "Nullam a sapien leo.",
    "Praesent cursus semper purus, vitae gravida risus dapibus mattis.",
    "Sed pellentesque nulla lorem, in commodo arcu feugiat sed.",
    "Phasellus blandit arcu non diam varius ornare."
  )
}
