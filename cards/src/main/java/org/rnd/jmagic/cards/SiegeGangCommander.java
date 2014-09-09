package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Siege-Gang Commander")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class SiegeGangCommander extends Card
{
	public static final class FourHundredBabies extends EventTriggeredAbility
	{
		public FourHundredBabies(GameState state)
		{
			super(state, "When Siege-Gang Commander enters the battlefield, put three 1/1 red Goblin creature tokens onto the battlefield.");

			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(3, 1, 1, "Put three 1/1 red Goblin creature tokens onto the battlefield.");
			token.setColors(Color.RED);
			token.setSubTypes(SubType.GOBLIN);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class Siege extends ActivatedAbility
	{
		public Siege(GameState state)
		{
			super(state, "(1)(R), Sacrifice a Goblin: Siege-Gang Commander deals 2 damage to target creature or player.");

			this.setManaCost(new ManaPool("1R"));

			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.GOBLIN), "Sacrifice a Goblin"));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Siege-Gang Commands deals 2 target creature or player."));
		}
	}

	public SiegeGangCommander(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new FourHundredBabies(state));

		this.addAbility(new Siege(state));
	}
}
