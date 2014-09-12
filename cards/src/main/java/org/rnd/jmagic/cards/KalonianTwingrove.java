package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kalonian Twingrove")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.TREEFOLK})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class KalonianTwingrove extends Card
{
	public static final class KalonianTwingroveAbility0 extends CharacteristicDefiningAbility
	{
		private final String creatureName;

		public KalonianTwingroveAbility0(GameState state)
		{
			this(state, "This creature");
		}

		public KalonianTwingroveAbility0(GameState state, String creatureName)
		{
			super(state, creatureName + "'s power and toughness are each equal to the number of Forests you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);
			this.creatureName = creatureName;

			SetGenerator numForests = Count.instance(Intersect.instance(HasSubType.instance(SubType.FOREST), ControlledBy.instance(You.instance())));
			this.addEffectPart(setPowerAndToughness(This.instance(), numForests, numForests));
		}

		@Override
		public KalonianTwingroveAbility0 create(Game game)
		{
			return new KalonianTwingroveAbility0(game.physicalState, this.creatureName);
		}
	}

	public static final class KalonianTwingroveAbility1 extends EventTriggeredAbility
	{
		public KalonianTwingroveAbility1(GameState state)
		{
			super(state, "When Kalonian Twingrove enters the battlefield, put a green Treefolk Warrior creature token onto the battlefield with \"This creature's power and toughness are each equal to the number of Forests you control.\"");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token_name = new CreateTokensFactory(1, 0, 0, "Put a green Treefolk Warrior creature token onto the battlefield with \"This creature's power and toughness are each equal to the number of Forests you control.\"");
			token_name.setColors(Color.GREEN);
			token_name.setSubTypes(SubType.TREEFOLK, SubType.WARRIOR);
			token_name.addAbility(KalonianTwingroveAbility0.class);
			this.addEffect(token_name.getEventFactory());
		}
	}

	public KalonianTwingrove(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Kalonian Twingrove's power and toughness are each equal to the number
		// of Forests you control.
		this.addAbility(new KalonianTwingroveAbility0(state, this.getName()));

		// When Kalonian Twingrove enters the battlefield, put a green Treefolk
		// Warrior creature token onto the battlefield with
		// "This creature's power and toughness are each equal to the number of Forests you control."
		this.addAbility(new KalonianTwingroveAbility1(state));
	}
}
