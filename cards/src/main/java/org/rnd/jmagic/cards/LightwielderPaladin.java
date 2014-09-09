package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lightwielder Paladin")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class LightwielderPaladin extends Card
{
	public static final class WieldsLight extends EventTriggeredAbility
	{
		public WieldsLight(GameState state)
		{
			super(state, "Whenever Lightwielder Paladin deals combat damage to a player, you may exile target black or red permanent that player controls.");

			// Whenever Lightwielder Paladin deals combat damage to a player,
			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			// you may exile target black or red permanent that player controls.
			SetGenerator blackAndRed = HasColor.instance(Color.BLACK, Color.RED);
			SetGenerator blackAndRedPermanents = Intersect.instance(blackAndRed, Permanents.instance());
			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator thatPlayerControls = ControlledBy.instance(thatPlayer);
			SetGenerator legalTargets = Intersect.instance(blackAndRedPermanents, thatPlayerControls);
			Target target = this.addTarget(legalTargets, "target black or red permanent");

			this.addEffect(youMay(exile(targetedBy(target), "Exile target black or red permanent that player controls"), "You may exile target black or red permanent that player controls."));
		}
	}

	public LightwielderPaladin(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// First strike (This creature deals combat damage before creatures
		// without first strike.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		this.addAbility(new WieldsLight(state));
	}
}
