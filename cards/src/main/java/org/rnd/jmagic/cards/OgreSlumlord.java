package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ogre Slumlord")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.OGRE})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class OgreSlumlord extends Card
{
	public static final class OgreSlumlordAbility0 extends EventTriggeredAbility
	{
		public OgreSlumlordAbility0(GameState state)
		{
			super(state, "Whenever another nontoken creature dies, you may put a 1/1 black Rat creature token onto the battlefield.");

			SetGenerator nonTokenCreatures = RelativeComplement.instance(HasType.instance(Type.CREATURE), Tokens.instance());
			SetGenerator another = RelativeComplement.instance(nonTokenCreatures, ABILITY_SOURCE_OF_THIS);
			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), another, true));

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 black Rat creature token onto the battlefield");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.RAT);
			this.addEffect(youMay(token.getEventFactory(), "You may put a 1/1 black Rat creature token onto the battlefield."));
		}
	}

	public static final class OgreSlumlordAbility1 extends StaticAbility
	{
		public OgreSlumlordAbility1(GameState state)
		{
			super(state, "Rats you control have deathtouch.");

			SetGenerator ratsYouControl = Intersect.instance(HasSubType.instance(SubType.RAT), ControlledBy.instance(You.instance()));
			this.addEffectPart(addAbilityToObject(ratsYouControl, org.rnd.jmagic.abilities.keywords.Deathtouch.class));
		}
	}

	public OgreSlumlord(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever another nontoken creature dies, you may put a 1/1 black Rat
		// creature token onto the battlefield.
		this.addAbility(new OgreSlumlordAbility0(state));

		// Rats you control have deathtouch.
		this.addAbility(new OgreSlumlordAbility1(state));
	}
}
