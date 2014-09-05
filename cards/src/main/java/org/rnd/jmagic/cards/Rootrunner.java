package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rootrunner")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Rootrunner extends Card
{
	public static final class RootrunnerAbility extends ActivatedAbility
	{
		public RootrunnerAbility(GameState state)
		{
			super(state, "(G)(G), Sacrifice Rootrunner: Put target land on the top of its owner's library");

			this.setManaCost(new ManaPool("GG"));

			// costs
			this.addCost(sacrificeThis("Rootrunner"));

			// targets
			SetGenerator lands = HasType.instance(Type.LAND);
			Target target = this.addTarget(Intersect.instance(lands, Permanents.instance()), "target land");

			// effects
			EventType.ParameterMap bounceParameters = new EventType.ParameterMap();
			bounceParameters.put(EventType.Parameter.CAUSE, This.instance());
			bounceParameters.put(EventType.Parameter.TO, LibraryOf.instance(OwnerOf.instance(targetedBy(target))));
			bounceParameters.put(EventType.Parameter.INDEX, numberGenerator(1));
			bounceParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, bounceParameters, "Put target land on the top of its owner's library"));
		}
	}

	public Rootrunner(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulshift(state, 3));
		this.addAbility(new RootrunnerAbility(state));
	}
}
