package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Boros Reckoner")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.WIZARD})
@ManaCost("(R/W)(R/W)(R/W)")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BorosReckoner extends Card
{
	public static final class BorosReckonerAbility0 extends EventTriggeredAbility
	{
		public BorosReckonerAbility0(GameState state)
		{
			super(state, "Whenever Boros Reckoner is dealt damage, it deals that much damage to target creature or player.");
			this.addPattern(whenThisIsDealtDamage());

			SetGenerator triggerDamage = TriggerDamage.instance(This.instance());
			SetGenerator thatMuch = Count.instance(triggerDamage);

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			this.addEffect(permanentDealDamage(thatMuch, target, "It deals that much damage to its controller."));
		}
	}

	public static final class BorosReckonerAbility1 extends ActivatedAbility
	{
		public BorosReckonerAbility1(GameState state)
		{
			super(state, "(RW): Boros Reckoner gains first strike until end of turn.");
			this.setManaCost(new ManaPool("(RW)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "Boros Reckoner gains first strike until end of turn."));
		}
	}

	public BorosReckoner(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever Boros Reckoner is dealt damage, it deals that much damage to
		// target creature or player.
		this.addAbility(new BorosReckonerAbility0(state));

		// (r/w): Boros Reckoner gains first strike until end of turn.
		this.addAbility(new BorosReckonerAbility1(state));
	}
}
