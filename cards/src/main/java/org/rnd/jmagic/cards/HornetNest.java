package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hornet Nest")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class HornetNest extends Card
{
	public static final class HornetNestAbility1 extends EventTriggeredAbility
	{
		public HornetNestAbility1(GameState state)
		{
			super(state, "Whenever Hornet Nest is dealt damage, put that many 1/1 green Insect creature tokens with flying and deathtouch onto the battlefield.");
			this.addPattern(whenThisIsDealtDamage());

			SetGenerator thatMany = Count.instance(TriggerDamage.instance(This.instance()));

			CreateTokensFactory insects = new CreateTokensFactory(thatMany, numberGenerator(1), numberGenerator(1), "Put that many 1/1 green Insect creature tokens with flying and deathtouch onto the battlefield.");
			insects.setColors(Color.GREEN);
			insects.setSubTypes(SubType.INSECT);
			insects.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			insects.addAbility(org.rnd.jmagic.abilities.keywords.Deathtouch.class);
			this.addEffect(insects.getEventFactory());
		}
	}

	public HornetNest(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Whenever Hornet Nest is dealt damage, put that many 1/1 green Insect
		// creature tokens with flying and deathtouch onto the battlefield. (Any
		// amount of damage a creature with deathtouch deals to a creature is
		// enough to destroy it.)
		this.addAbility(new HornetNestAbility1(state));
	}
}
