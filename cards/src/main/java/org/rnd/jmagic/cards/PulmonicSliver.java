package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Pulmonic Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class PulmonicSliver extends Card
{
	@Name("\"If this permanent would be put into a graveyard, you may put it on top of its owner's library instead.\"")
	public final static class GraveyardReplacement extends StaticAbility
	{
		public GraveyardReplacement(GameState state)
		{
			super(state, "If this permanent would be put into a graveyard, you may put it on top of its owner's library instead.");

			ZoneChangeReplacementEffect zcre = new ZoneChangeReplacementEffect(state.game, "If this permanent would be put into a graveyard, you may put it on top of its owner's library instead.");
			zcre.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), This.instance(), true));
			zcre.changeDestination(LibraryOf.instance(OwnerOf.instance(This.instance())), 1);
			zcre.makeOptional(You.instance());

			this.addEffectPart(replacementEffectPart(zcre));
		}
	}

	public PulmonicSliver(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// All Sliver creatures have flying.
		this.addAbility(new org.rnd.jmagic.abilities.AllSliverCreaturesHave(state, org.rnd.jmagic.abilities.keywords.Flying.class, "All Sliver creatures have flying."));

		// All Slivers have
		// "If this permanent would be put into a graveyard, you may put it on top of its owner's library instead."
		this.addAbility(new org.rnd.jmagic.abilities.AllSliversHave(state, GraveyardReplacement.class, "All Slivers have \"If this permanent would be put into a graveyard, you may put it on top of its owner's library instead.\""));
	}
}
