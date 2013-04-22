package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Moldgraf Monstrosity")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("4GGG")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MoldgrafMonstrosity extends Card
{
	public static final class MoldgrafMonstrosityAbility1 extends EventTriggeredAbility
	{
		public MoldgrafMonstrosityAbility1(GameState state)
		{
			super(state, "When Moldgraf Monstrosity dies, exile it, then return two creature cards at random from your graveyard to the battlefield.");
			this.addPattern(whenThisDies());

			SetGenerator it = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			this.addEffect(exile(it, "Exile it."));

			EventFactory random = new EventFactory(RANDOM, "Two random creature cards in your graveyard.");
			random.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))));
			random.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addEffect(random);

			this.addEffect(putOntoBattlefield(EffectResult.instance(random), "Return two creature cards at random from your graveyard to the battlefield."));
		}
	}

	public MoldgrafMonstrosity(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// When Moldgraf Monstrosity dies, exile it, then return two creature
		// cards at random from your graveyard to the battlefield.
		this.addAbility(new MoldgrafMonstrosityAbility1(state));
	}
}
