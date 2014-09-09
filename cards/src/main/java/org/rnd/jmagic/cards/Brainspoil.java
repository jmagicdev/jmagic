package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brainspoil")
@Types({Type.SORCERY})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class Brainspoil extends Card
{
	public Brainspoil(GameState state)
	{
		super(state);

		SetGenerator auras = Intersect.instance(HasSubType.instance(SubType.AURA), Permanents.instance());
		Target target = this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), EnchantedBy.instance(auras)), "target creature that isn't enchanted");

		// Destroy target creature that isn't enchanted. It can't be
		// regenerated.
		this.addEffects(bury(this, targetedBy(target), "Destroy target creature that isn't enchanted. It can't be regenerated."));

		// Transmute {1}{B}{B} ({1}{B}{B}, Discard this card: Search your
		// library for a card with the same converted mana cost as this card,
		// reveal it, and put it into your hand. Then shuffle your library.
		// Transmute only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Transmute(state, "(1)(B)(B)"));
	}
}
