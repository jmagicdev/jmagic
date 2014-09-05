package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Spell Contortion")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SpellContortion extends Card
{
	public SpellContortion(GameState state)
	{
		super(state);

		// Multikicker (1)(U)
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(1)(U)");
		this.addAbility(ability);

		// Counter target spell unless its controller pays (2).
		Target target = this.addTarget(Spells.instance(), "target spell");
		this.addEffect(counterTargetUnlessControllerPays("(2)", target));

		// Draw a card for each time Spell Contortion was kicked.
		this.addEffect(drawCards(You.instance(), ThisSpellWasKicked.instance(ability.costCollections[0]), "Draw a card for each time Spell Contortion was kicked."));
	}
}
