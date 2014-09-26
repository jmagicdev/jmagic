package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Forlorn Pseudamma")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class ForlornPseudamma extends Card
{
	public static final class ForlornPseudammaAbility1 extends EventTriggeredAbility
	{
		public ForlornPseudammaAbility1(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Forlorn Pseudamma becomes untapped, you may pay (2)(B). If you do, put a 2/2 black Zombie enchantment creature token onto the battlefield.");
			this.addPattern(inspired());

			EventFactory mayPay = youMayPay("(2)(B)");

			CreateTokensFactory zombies = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie enchantment creature token onto the battlefield.");
			zombies.setColors(Color.BLACK);
			zombies.setSubTypes(SubType.ZOMBIE);
			zombies.setEnchantment();

			this.addEffect(ifThen(mayPay, zombies.getEventFactory(), "You may pay (2)(B). If you do, put a 2/2 black Zombie enchantment creature token onto the battlefield."));
		}
	}

	public ForlornPseudamma(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Intimidate
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		// Inspired \u2014 Whenever Forlorn Pseudamma becomes untapped, you may
		// pay (2)(B). If you do, put a 2/2 black Zombie enchantment creature
		// token onto the battlefield.
		this.addAbility(new ForlornPseudammaAbility1(state));
	}
}
