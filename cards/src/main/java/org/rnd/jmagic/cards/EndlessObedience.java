package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Endless Obedience")
@Types({Type.SORCERY})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class EndlessObedience extends Card
{
	public EndlessObedience(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Put target creature card from a graveyard onto the battlefield under
		// your control.
		SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
		SetGenerator creaturesInYards = Intersect.instance(HasType.instance(Type.CREATURE), inGraveyards);
		SetGenerator target = targetedBy(this.addTarget(creaturesInYards, "target creature card from a graveyard"));

		this.addEffect(putOntoBattlefield(target, "Put target creature card from a graveyard onto the battlefield under your control."));
	}
}
