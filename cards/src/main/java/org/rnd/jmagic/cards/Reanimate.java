package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Reanimate")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Tempest.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Reanimate extends Card
{
	public Reanimate(GameState state)
	{
		super(state);

		// Put target creature card from a graveyard onto the battlefield under
		// your control. You lose life equal to its converted mana cost.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance()))), "target creature card in a graveyard"));
		this.addEffect(putOntoBattlefield(target, "Put target creature card from a graveyard onto the battlefield under your control."));
		this.addEffect(loseLife(You.instance(), ConvertedManaCostOf.instance(target), "You lose life equal to its converted mana cost."));
	}
}
