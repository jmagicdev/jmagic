package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cloudgoat Ranger")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GIANT})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class CloudgoatRanger extends Card
{
	public static final class CloudgoatRangerAbility0 extends EventTriggeredAbility
	{
		public CloudgoatRangerAbility0(GameState state)
		{
			super(state, "When Cloudgoat Ranger enters the battlefield, put three 1/1 white Kithkin Soldier creature tokens onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory tokens = new CreateTokensFactory(3, 1, 1, "Put three 1/1 white Kithkin Soldier creature tokens onto the battlefield.");
			tokens.setColors(Color.WHITE);
			tokens.setSubTypes(SubType.KITHKIN, SubType.SOLDIER);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public static final class CloudgoatRangerAbility1 extends ActivatedAbility
	{
		public CloudgoatRangerAbility1(GameState state)
		{
			super(state, "Tap three untapped Kithkin you control: Cloudgoat Ranger gets +2/+0 and gains flying until end of turn.");

			// Tap three untapped Kithkin you control
			SetGenerator kithkin = Intersect.instance(Untapped.instance(), HasSubType.instance(SubType.KITHKIN), ControlledBy.instance(You.instance()));
			EventFactory factory = new EventFactory(EventType.TAP_CHOICE, "Tap three untapped Kithkin you control");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.CHOICE, kithkin);
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addCost(factory);

			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, 0, "Cloudgoat Ranger gets +2/+0 and gains flying until end of turn.", org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public CloudgoatRanger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Cloudgoat Ranger enters the battlefield, put three 1/1 white
		// Kithkin Soldier creature tokens onto the battlefield.
		this.addAbility(new CloudgoatRangerAbility0(state));

		// Tap three untapped Kithkin you control: Cloudgoat Ranger gets +2/+0
		// and gains flying until end of turn.
		this.addAbility(new CloudgoatRangerAbility1(state));
	}
}
