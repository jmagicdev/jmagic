package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Meloku the Clouded Mirror")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.MOONFOLK, SubType.WIZARD})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MelokutheCloudedMirror extends Card
{
	public static final class MelokutheCloudedMirrorAbility1 extends ActivatedAbility
	{
		public MelokutheCloudedMirrorAbility1(GameState state)
		{
			super(state, "(1), Return a land you control to its owner's hand: Put a 1/1 blue Illusion creature token with flying onto the battlefield.");

			this.setManaCost(new ManaPool("(1)"));

			EventFactory bounce = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return a land you control to its owner's hand");
			bounce.parameters.put(EventType.Parameter.CAUSE, This.instance());
			bounce.parameters.put(EventType.Parameter.PLAYER, You.instance());
			bounce.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())));
			bounce.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addCost(bounce);

			// Put a 1/1 blue Illusion creature token with flying onto the
			// battlefield.
			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 blue Illusion creature token with flying onto the battlefield.");
			token.setColors(Color.BLUE);
			token.setSubTypes(SubType.ILLUSION);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public MelokutheCloudedMirror(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1), Return a land you control to its owner's hand: Put a 1/1 blue
		// Illusion creature token with flying onto the battlefield.
		this.addAbility(new MelokutheCloudedMirrorAbility1(state));
	}
}
