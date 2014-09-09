package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gorgon Flail")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({})
public final class GorgonFlail extends Card
{
	public GorgonFlail(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+1 and has deathtouch. (Creatures dealt
		// damage by this creature are destroyed. You can divide its combat
		// damage among any of the creatures blocking or blocked by it.)
		SetGenerator equippedCreature = EquippedBy.instance(This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, equippedCreature, "Equipped creature", +1, +1, org.rnd.jmagic.abilities.keywords.Deathtouch.class, false));

		// Equip {2} ({2}: Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
