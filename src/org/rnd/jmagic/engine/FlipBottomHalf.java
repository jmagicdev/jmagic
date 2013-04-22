package org.rnd.jmagic.engine;

/**
 * 709.1. Flip cards have a two-part card frame on a single card. The text that
 * appears right side up on the card defines the card's normal characteristics.
 * Additional alternative characteristics appear upside down on the card.
 * 
 * 709.1a The top half of a flip card contains the card's normal name, text box,
 * type line, power, and toughness. The text box usually contains an ability
 * that causes the permanent to "flip" if certain conditions are met.
 * 
 * 709.1b The bottom half of a flip card contains an alternative name, text box,
 * type line, power, and toughness. These characteristics are used only if the
 * permanent is on the battlefield and only if the permanent is flipped.
 * 
 * Extend this class when you want to write the other half of a flip card. The
 * top half should be the card's "normal" characteristics, and set
 * {@link GameObject#flippedClass} to represent that card's alternative
 * characteristics. Annotate the instance of this class with a {@link Flipped}
 * annotation, setting the value to the top half of the flip card.
 */
public abstract class FlipBottomHalf extends AlternateCard
{
	public FlipBottomHalf(GameState state)
	{
		super(state);

		Class<? extends Card> topHalfClass = this.getClass().getAnnotation(Flipped.class).value();
		this.setManaCost(new ManaPool(topHalfClass.getAnnotation(ManaCost.class).value()));
	}
}
