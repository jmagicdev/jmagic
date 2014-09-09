package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dryad Militant")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.DRYAD})
@ManaCost("(G/W)")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class DryadMilitant extends Card
{
	public static final class DryadMilitantAbility0 extends StaticAbility
	{
		public DryadMilitantAbility0(GameState state)
		{
			super(state, "If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead");
			replacement.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), HasType.instance(Type.INSTANT, Type.SORCERY), true));
			replacement.changeDestination(ExileZone.instance());

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public DryadMilitant(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// If an instant or sorcery card would be put into a graveyard from
		// anywhere, exile it instead.
		this.addAbility(new DryadMilitantAbility0(state));
	}
}
