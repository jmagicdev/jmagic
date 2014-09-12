package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hunter's Ambush")
@Types({Type.INSTANT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class HuntersAmbush extends Card
{
	public HuntersAmbush(GameState state)
	{
		super(state);

		// Prevent all combat damage that would be dealt by nongreen creatures
		// this turn.
		SetGenerator nongreenCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.GREEN));
		ReplacementEffect foggy = new org.rnd.jmagic.abilities.PreventCombatDamage(this.game, nongreenCreatures, "Prevent all combat damage that would be dealt by nongreen creatures this turn.");
		this.addEffect(createFloatingReplacement(foggy, "Prevent all combat damage that would be dealt by nongreen creatures this turn."));
	}
}
