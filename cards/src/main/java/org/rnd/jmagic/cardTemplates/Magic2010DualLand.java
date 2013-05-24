package org.rnd.jmagic.cardTemplates;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Magic2010DualLand extends Card
{
	public Magic2010DualLand(GameState state, SubType a, SubType b)
	{
		super(state);

		// ~ enters the battlefield tapped unless you control a {A} or a {B}.
		SetGenerator landTypes = HasSubType.instance(a, b);
		SetGenerator condition = Intersect.instance(landTypes, ControlledBy.instance(You.instance()));

		String conditionText = "you control " + //
		(a.toString().matches("[aeiouAEIOU].*") ? "an " : "a ") + a + " or " + //
		(b.toString().matches("[aeiouAEIOU].*") ? "an " : "a ") + b;
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTappedUnless(state, this.getName(), condition, conditionText));

		// (T): Add {A} or {B} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(" + Color.getColorForType(a).getLetter() + Color.getColorForType(b).getLetter() + ")"));
	}
}
