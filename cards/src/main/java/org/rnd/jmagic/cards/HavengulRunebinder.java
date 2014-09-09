package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Havengul Runebinder")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class HavengulRunebinder extends Card
{
	public static final class HavengulRunebinderAbility0 extends ActivatedAbility
	{
		public HavengulRunebinderAbility0(GameState state)
		{
			super(state, "(2)(U), (T), Exile a creature card from your graveyard: Put a 2/2 black Zombie creature token onto the battlefield, then put a +1/+1 counter on each Zombie creature you control.");
			this.setManaCost(new ManaPool("(2)(U)"));
			this.costsTap = true;
			// Exile a creature card from your graveyard
			this.addCost(exile(You.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), 1, "Exile a creature card from your graveyard"));

			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield,");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.ZOMBIE);
			this.addEffect(token.getEventFactory());

			SetGenerator zombies = Intersect.instance(HasSubType.instance(SubType.ZOMBIE), CREATURES_YOU_CONTROL);
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, zombies, "then put a +1/+1 counter on each Zombie creature you control."));
		}
	}

	public HavengulRunebinder(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (2)(U), (T), Exile a creature card from your graveyard: Put a 2/2
		// black Zombie creature token onto the battlefield, then put a +1/+1
		// counter on each Zombie creature you control.
		this.addAbility(new HavengulRunebinderAbility0(state));
	}
}
