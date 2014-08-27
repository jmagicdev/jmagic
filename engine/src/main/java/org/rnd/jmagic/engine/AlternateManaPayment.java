package org.rnd.jmagic.engine;

/**
 * This class represents a way of paying mana that isn't spending mana. This
 * isn't an alternate cost, like Force of Will, this is an alternate payment,
 * like Convoke or Delve.
 * 
 * GameObject uses a Set of these, so make sure you implement hashCode and
 * equals.
 */
public abstract class AlternateManaPayment
{
	/**
	 * Instructs the player to use this alternate mana payment to pay for some
	 * or all of the given mana cost.
	 * 
	 * @param cost The cost the player will pay. Modify this parameter in-place
	 * during the execution of the payment, removing the symbols that were paid
	 * via the alternate payment.
	 * @param source The source of the alternate payment, usually an object
	 * being cast whose mana cost may be paid by this alternate payment.
	 */
	public abstract void pay(ManaPool cost, GameObject source);
}
