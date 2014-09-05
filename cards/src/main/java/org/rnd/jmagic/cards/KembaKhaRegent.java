package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Kemba, Kha Regent")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.CAT})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class KembaKhaRegent extends Card
{
	public static final class KembaKhaRegentAbility0 extends EventTriggeredAbility
	{
		public KembaKhaRegentAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a 2/2 white Cat creature token onto the battlefield for each Equipment attached to Kemba, Kha Regent.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			CreateTokensFactory factory = new CreateTokensFactory(Count.instance(Intersect.instance(AttachedTo.instance(ABILITY_SOURCE_OF_THIS), HasSubType.instance(SubType.EQUIPMENT))), "Put a 2/2 white Cat creature token onto the battlefield for each Equipment attached to Kemba, Kha Regent.");
			factory.addCreature(2, 2);
			factory.setColors(Color.WHITE);
			factory.setSubTypes(SubType.CAT);
			this.addEffect(factory.getEventFactory());
		}
	}

	public KembaKhaRegent(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// At the beginning of your upkeep, put a 2/2 white Cat creature token
		// onto the battlefield for each Equipment attached to Kemba, Kha
		// Regent.
		this.addAbility(new KembaKhaRegentAbility0(state));
	}
}
