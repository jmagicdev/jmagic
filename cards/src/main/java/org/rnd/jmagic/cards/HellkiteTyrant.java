package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hellkite Tyrant")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class HellkiteTyrant extends Card
{
	public static final class HellkiteTyrantAbility1 extends EventTriggeredAbility
	{
		public HellkiteTyrantAbility1(GameState state)
		{
			super(state, "Whenever Hellkite Tyrant deals combat damage to a player, gain control of all artifacts that player controls.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(thatPlayer)));
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect(Empty.instance(), "Gain control of all artifacts that player controls.", controlPart));
		}
	}

	public static final class HellkiteTyrantAbility2 extends EventTriggeredAbility
	{
		public HellkiteTyrantAbility2(GameState state)
		{
			super(state, "At the beginning of your upkeep, if you control twenty or more artifacts, you win the game.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.interveningIf = Intersect.instance(Count.instance(Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance()))), Between.instance(20, null));

			this.addEffect(youWinTheGame());
		}
	}

	public HellkiteTyrant(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// Flying, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever Hellkite Tyrant deals combat damage to a player, gain
		// control of all artifacts that player controls.
		this.addAbility(new HellkiteTyrantAbility1(state));

		// At the beginning of your upkeep, if you control twenty or more
		// artifacts, you win the game.
		this.addAbility(new HellkiteTyrantAbility2(state));
	}
}
