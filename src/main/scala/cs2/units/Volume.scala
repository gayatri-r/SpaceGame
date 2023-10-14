package cs2.units

class Volume {
  //Field - the volume stored in LITERS
  private var lit:Double = 0.0

  //Basic math operators to add, subtract, and scale volumes
  def + (other:Volume):Volume = { Volume(this.lit + other.lit) }
  def += (other:Volume):Unit  = { this.lit += other.lit }

  def - (other:Volume):Volume = { Volume(this.lit - other.lit) }
  def -= (other:Volume):Unit  = { this.lit -= other.lit }

  def * (scalar:Double):Volume = { Volume(this.lit * scalar) }
  def *= (scalar:Double):Unit  = { this.lit *= scalar }

  def / (scalar:Double):Volume = { Volume(this.lit / scalar) }
  def /= (scalar:Double):Unit  = { this.lit /= scalar }

  //Getter functions that return in a variety of units
  def liters():Double = { this.lit }
  
  def milliliters():Double = { this.lit * 1000 }

  def gallons():Double = { this.lit / 3.785 }

  def quarts():Double = { this.lit * 1.057 }

  def pints():Double = { this.lit * 2.113 }

  def cups():Double = { this.lit * 4.167 }

  def teaspoons():Double = { this.lit * 202.9 }

  def tablespoons():Double = { this.lit * 67.628 }
}

object Volume {
  //"Constructor" apply methods operating in liters
  def apply():Volume = { new Volume }
  def apply(amt:Double):Volume = {
    val v = new Volume
    v.lit = amt
    v
  }
  //Alternative "static" methods to create volumes in other units
  def liters(amt:Double):Volume = {
    val v = new Volume
    v.lit = amt
    v
  } //identical to an apply method
  
  def milliliters(amt:Double):Volume = {
    val v = new Volume
    v.lit = amt / 1000
    v
  }

  def gallons(amt:Double):Volume = {
    val v = new Volume
    v.lit = amt * 3.785
    v
  }

  def quarts(amt:Double):Volume = {
    val v = new Volume
    v.lit = amt / 1.057
    v
  }

  def pints(amt:Double):Volume = {
    val v = new Volume
    v.lit = amt / 2.113
    v
  }

  def cups(amt:Double):Volume = {
    val v = new Volume
    v.lit = amt / 4.167
    v
  }

  def teaspoons(amt:Double):Volume = {
    val v = new Volume
    v.lit = amt / 202.9
    v
  }

  def tablespoons(amt:Double):Volume = {
    val v = new Volume
    v.lit = amt / 67.628
    v
  }
}
